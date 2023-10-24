
# the number should be the ideal starting number of SOL group - 1
# example: I wanna my SOL group start to count from 384, then the count = 383 
count = 383

# change the file name to the file that you want to modify and define the name of output file
with open('4coo_ee_new4.pdb', 'r') as input_file, open('4coo_ee_new444.pdb', 'w') as output_file:
    for line in input_file:
        # restart when the SOL number is over 99999
        if count == 9999:
            if split_line[2] == "HW2":
                count = -1
        
        if line.startswith('ATOM'):
            split_line = line.split()
        
            if split_line[3] == "SOL":
                if split_line[2] == "OW":
                    count = count + 1
                    
                atom_type = split_line[0]
                atom_number = int(split_line[1])
                atom_name = split_line[2]
                residue_name = split_line[3]
                residue_number = count
                x_coord = float(split_line[5])
                y_coord = float(split_line[6])
                z_coord = float(split_line[7])
                occupancy = float(split_line[8])
                temp_factor = float(split_line[9])
                element = split_line[10]                

                output_file.write('{:<6s}{:>5d}{:1s}{:^4s}{:1s}{:<5s}{:>4}{:>12.3f}{:>8.3f}{:>8.3f}{:>6.2f}{:>6.2f}{:10s}{:>2s}\n'.format(
                atom_type, atom_number, '', atom_name, '', residue_name, residue_number, x_coord, y_coord, z_coord, occupancy, temp_factor,'', element))
        # only the lines for SOL groups will be changed, the rest keep the same as before
            else:
                output_file.write(line)
        else:
                output_file.write(line)
