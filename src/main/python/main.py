#*******
#* Read input from STDIN
#* Use print to output your result to STDOUT.
#* Use sys.stderr.write() to display debugging information to STDERR
#* ***/
import sys

lines = []
for line in sys.stdin:
	lines.append(line.rstrip('\n'))
lines.pop(0)

result = {}
for line in lines:
    parts = line.split(" ")
    if parts[1] not in result:
        result[parts[1]] = []
    result[parts[1]].append(parts[0])
for key in result:
    if len(result[key]) == 1:
        print(result[key][0])
