set ns [new Simulator]
set ntrace [open lab10.tr w]

$ns trace-all $ntrace
set namfile [open lab10.nam w]
$ns namtrace-all $namfile

proc Finish {} {
  global ns ntrace namfile
  $ns flush-trace
  close $ntrace
  close $namfile
  exec nam lab10.nam &
  exec grep -c "^d" lab10.tr &
  exit 0
}

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail

$ns queue-limit $n0 $n1 10
$ns queue-limit $n1 $n2 10

set tcp0 [new Agent/TCP]
set sink0 [new Agent/TCPSink]
$ns attach-agent $n0 $tcp0
$ns attach-agent $n2 $sink0
$ns connect $tcp0 $sink0

set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 100
$cbr0 set rate_ 1Mb
$cbr0 attach-agent $tcp0

$ns at 0.0 "$cbr0 start"
$ns at 5.0 "Finish"
$ns run
