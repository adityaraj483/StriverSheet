package DS;

import javax.swing.event.ListDataListener;
import java.util.*;
import java.util.stream.Collectors;


//Q2: Given an array of start time for N processes that have the same duration of completion.
// Given M CPUs, determine the minimum time when all processes will be completed.
//Suggested an binary search approach, to determine the lower bound for the process start time
// when no CPU is available. This number will tell us when the process would start. BUT I messed up
// here, I didn't realise that, if the lower bound is not zero (index) then the index CPUs would be
// freed up by then, and the process would start from its expected start time
public class test {


}






