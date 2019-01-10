let x = 
   let y = 1 + 2 in y
in let rec succ a = a + 1 
in let rec mul b c = b *. c 
in print_int (succ (mul b c))
