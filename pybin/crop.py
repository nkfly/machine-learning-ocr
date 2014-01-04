

f = open("ml2013final_train.dat")

count = 0
dataset = []

#  
#  Read Data
#             ============================
for line in f:
    count = count + 1
    arr = line.split(" ")
    z   = int(arr[0])
    arr = arr[1:]
    arr = [ x.split(":") for x in arr ]
    arr = [ [int(x[0]), float(x[1])] for x in arr ]

    obj = {"line": count,"z":z , "arr": arr }

    dataset.append(obj)


def region(pixel):
    threshold = 105 / 2
    if pixel / 105 < threshold: #up
        if pixel % 105 < threshold: #left
            return 1
        else: #right
            return 2

    else: #down
        if pixel % 105 < threshold: #left
            return 3
        else: #right
            return 4
        
w = open("crop.out","w")

_gradient = 0.1

_line = 0

for obj in dataset:
    _left = 105
    _right = 0
    _top  = 105
    _bottom = 0

    for elem in obj["arr"]:
        if(elem[1] > _gradient and elem[0] % 105 < _left):
            _left = elem[0] % 105
        if(elem[1] > _gradient and elem[0] % 105 > _right):
            _right = elem[0] % 105
        if(elem[1] > _gradient and elem[0] / 105 < _top):
            _top = elem[0] / 105
        if(elem[1] > _gradient and elem[0] / 105 > _bottom):
            _bottom = elem[0] / 105
    
    _line = _line +  1
    
    _wf = open("crop_dir/"+str(_line),"w")

    output = ""

    output = output + str(obj["z"])


    _idx = 0
    for elem in obj["arr"]:
            

        if(elem[1] <= _gradient):
            continue

        if (_right - _left) <= 0 or (_bottom - _top) <= 0:
            continue

        row = elem[0] / 105
        col = elem[0] % 105

        #TODO: crop
        _rr = int(float( row - _top) * 105 / (_bottom - _top )) 
        _rc = int(float( col - _left) * 105 / (_right - _left ))

        #TODO: reshape 
        elem[0] = _rr * 105 + _rc
        if(elem[0] <= _idx):
            continue

        _idx = elem[0]

        if elem[0] <= 0 or elem[0] >= 105 * 105:

            print elem[0]
            continue        

        output = output + " "+ str(elem[0])+":"+str(elem[1])
    
    output = output + "\n"

    w.write(output)  
    _wf.write(output)
    _wf.close()
    
w.close()

