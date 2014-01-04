import commands
import os

_g = 0.1

for i in range(3,8):
    for j in range(3,8):
        cmd = ("make downsample_custom XX="+ str(i) +" YY=" + str(j) + " DOWNSAMPLED_CUSTOMFILE=downsampled_" + 
                str(i) + "x" + str(j) + ".dat")
        os.system(cmd)

    '''
    w = open("accu_"+str(_g+i*0.1),"w")
    c = commands.getstatusoutput("")
    w.write(str())
    b[1]
    '''


