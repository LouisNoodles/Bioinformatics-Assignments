# change the file name (.gro/.pdb) that you are gonna use to find the center atom
with open("npt_helix_AA.gro", "r") as fo:
    lines = fo.readlines()
# define variables in 3 dimensions
center_X = 0 
center_Y = 0
center_Z = 0 

# 'pro_start' defines the first line in the file that is gonna to be read
# 'pro_num' defines the last line in the file that is gonna to be read
pro_start = 3
pro_num = 216

# iteration of every atom of the protein to calculate the geometry center
# Since python start from 0 to count, therefore, pro_start - 1
for line in lines[pro_start-1:pro_start+pro_num-1]:
    # seperate each line by spaces
    items = line[20:44].split()
    # read the coordinates of each atom
    center_X += float(items[0])
    center_Y += float(items[1])
    center_Z += float(items[2])
# calculate the geometry center
center_X = center_X/pro_num
center_Y = center_Y/pro_num
center_Z = center_Z/pro_num

# define a variable that stores the information of the atom that is closet to the geometry center
# atom_info records the number of that atom
atom_info = ""
# dist records the distance between the atom and the geometry center
dist = 10
# iteration again to find the atom that is closet to the geometry center
for line in lines[pro_start-1:pro_start+pro_num-1]:
    items = line[20:44].split()
    x = float(items[0])
    y = float(items[1])
    z = float(items[2])
    
    # calculate the distance, if the new distance is smaller than that stores in the variable 'dist',
    # update both 'atom_info' and 'dist'
    atom_center_dist = ((x-center_X)**2 + (y-center_Y)**2 + (z-center_Z)**2 )**0.5
    if atom_center_dist < dist:
        dist = atom_center_dist
        # record the information of this atom
        atom_info = line
# output the information of this atom
print(atom_info)