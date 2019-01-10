let x = 
   let y = 1 + 2 in y
in let rec succ a = a + 1 
in let rec double b = 2 *. b 
in print_int (succ (double x))
