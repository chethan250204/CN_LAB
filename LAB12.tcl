set ns [new Simulator]

$ns color 1 Blue
$ns color 2 Red

set ntrace [open lab12.tr w]
$ns trace-all $ntrace
set namfile [open lab12.nam w]
$ns namtrace-all $namfile

proc Finish {} {
  global ns ntrace namfile
  $ns flush-trace
  close $ntrace
  close $namfile
  exec nam lab12.nam &
  exit 0
}

for {set i 0} {$i < 6} {incr i} {
set n($i) [$ns node]
}

$ns duplex-link $n(0) $n(2) 2Mb 10ms DropTail
$ns duplex-link $n(1) $n(2) 2Mb 10ms DropTail
$ns duplex-link $n(2) $n(3) 0.6Mb 100ms DropTail
$ns newLan "$n(3) $n(4) $n(5)" 0.5Mb 40ms LL Queue/DropTail MAC/802_3
Channel

set tcp0 [new Agent/TCP/Newreno]
$tcp0 set fid_ 1
$ns attach-agent $n(0) $tcp0

set sink0 [new Agent/TCPSink/DelAck]
$ns attach-agent $n(4) $sink0
$ns connect $tcp0 $sink0

set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

$ns at 0.1 "$ftp0 start"
$ns at 10.0 "$ftp0 stop"
$ns at 10.1 "Finish"

$ns run
