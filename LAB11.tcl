set ns [new Simulator]
set ntrace [open lab11.tr w]
$ns trace-all $ntrace
set namfile [open lab11.nam w]
$ns namtrace-all $namfile

proc Finish {} {
  global ns ntrace namfile
  $ns flush-trace
  close $ntrace
  close $namfile
  exec nam lab11.nam &
  exec grep -c "^d.*ping" lab11.tr &
  exit 0
}

for {set i 0} {$i < 6} {incr i} {
set n($i) [$ns node]
}

for {set j 0} {$j < 5} {incr j} {
$ns duplex-link $n($j) $n([expr $j+1]) 0.1Mb 10ms DropTail
}

Agent/Ping instproc recv {from rtt} {
$self instvar node_
puts "node [$node_ id] received ping from $from with RTT $rtt ms"
}

set p0 [new Agent/Ping]
set p1 [new Agent/Ping]
$ns attach-agent $n(0) $p0
$ns attach-agent $n(5) $p1
$ns connect $p0 $p1

$ns queue-limit $n(2) $n(3) 2

set tcp0 [new Agent/TCP]
set sink0 [new Agent/TCPSink]
$ns attach-agent $n(2) $tcp0
$ns attach-agent $n(4) $sink0
$ns connect $tcp0 $sink0

set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set rate_ 1Mb
$cbr0 attach-agent $tcp0

foreach {t event} {
  0.2 "$p0 send"
  0.4 "$p1 send"
  0.4 "$cbr0 start"
  0.8 "$p0 send"
  1.0 "$p1 send"
  1.2 "$cbr0 stop"
  1.4 "$p0 send"
  1.6 "$p1 send"
  1.8 "Finish"
} {
  $ns at $t $event
}
$ns run
