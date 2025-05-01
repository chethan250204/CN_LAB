# Create the simulator object
set ns [new Simulator]

# Set up the topography for the simulation area (1500m x 1500m)
set topo [new Topography]
$topo load_flatgrid 1500 1500

# Open trace and NAM output files
set tracefile [open lab13.tr w]
set namfile [open lab13.nam w]

# Enable tracing for the simulation
$ns trace-all $tracefile
$ns namtrace-all $namfile
$ns namtrace-all-wireless $namfile 1500 1500

# Configure the wireless nodes with DSDV routing
$ns node-config -adhocRouting DSDV \
    -llType LL \
    -macType Mac/802_11 \
    -ifqType Queue/DropTail \
    -ifqLen 20 \
    -phyType Phy/WirelessPhy \
    -channelType Channel/WirelessChannel \
    -propType Propagation/TwoRayGround \
    -antType Antenna/OmniAntenna \
    -topoInstance $topo \
    -agentTrace ON \
    -routerTrace ON

# Create a General Operations Director (GOD) for 6 nodes
create-god 6

# Create 6 nodes with specific coordinates
foreach {id x y} {
    0 630 501
    1 454 340
    2 785 326
    3 270 190
    4 539 131
    5 964 177
} {
    set n$id [$ns node]
    $n$id set X_ $x
    $n$id set Y_ $y
    $n$id set Z_ 0.0
    $ns initial_node_pos $n$id 20
}

# Setup UDP connection: n0 → n4
set udp0 [new Agent/UDP]
set null1 [new Agent/Null]
$ns attach-agent $n0 $udp0
$ns attach-agent $n4 $null1
$ns connect $udp0 $null1
$udp0 set packetSize_ 1500

# Setup TCP connection: n3 → n5
set tcp0 [new Agent/TCP]
set sink1 [new Agent/TCPSink]
$ns attach-agent $n3 $tcp0
$ns attach-agent $n5 $sink1
$ns connect $tcp0 $sink1

# Attach a CBR (Constant Bit Rate) traffic generator to UDP
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0
$cbr0 set packetSize_ 1000
$cbr0 set rate_ 1.0Mb

# Attach an FTP application to TCP
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

# Define the finish procedure to end the simulation and print drop stats
proc finish {} {
    global ns tracefile namfile
    $ns flush-trace
    close $tracefile
    close $namfile
    exec nam lab13.nam &
    exec echo "Number of packets dropped is:" &
    exec grep -c "^D" lab13.tr &
    exit 0
}

# Schedule events: when to start and stop CBR and FTP
foreach {time action} {
    1.0 "$cbr0 start"
    2.0 "$ftp0 start"
    180.0 "$ftp0 stop"
    200.0 "$cbr0 stop"
    200.0 "finish"
} {
    $ns at $time $action
}

# Set movement for node n4 at different times
foreach {time x y} {
    70 100 60
    100 700 300
    150 900 200
} {
    $ns at $time "$n4 set dest $x $y 20"
}

# Run the simulation
$ns run
