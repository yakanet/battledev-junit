<?php
/*******
 * Read input from STDIN
 * Use echo or print to output your result to STDOUT, use the PHP_EOL constant at the end of each result line.
 * Use:
 *     fwrite(STDERR, "hello, world!" . PHP_EOL);
 * or
 *		error_log("hello, world!" . PHP_EOL);
 * to output debugging information to STDERR
 * ***/

do{
    $f = stream_get_line(STDIN, 10000, PHP_EOL);
    if($f!==false){
        $input[] = $f;
    }
}while($f!==false);
array_shift($input);

/* Vous pouvez aussi effectuer votre traitement ici après avoir lu toutes les données */

$result = [];
foreach($input as $line) {
    $parts = explode(" ", $line);
    if(!array_key_exists($parts[1], $result)) {
        $result[$parts[1]] = [];
    }
    $result[$parts[1]][] = $parts[0];
}
foreach($result as $key => $value) {
    if(count($value) == 1) {
        echo $value[0] . PHP_EOL;
    }
}
?>
