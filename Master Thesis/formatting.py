# Open the input and output files

# to distinguish between protein residual groups and others that are listed below, which don't have a chain ID
# can be modified based on purpose 
spec = ["HEMEA", "HEMEB", "SOL", "NA", "CL"]

count = 0
# change the file name to the file that you want to modify and define the name of output file
with open('frame225_new4.pdb', 'r') as input_file, open('4coo_ee_new4.pdb', 'w') as output_file:
    for line in input_file:
        # restart when the number is over 99999
        if count == 99999:
            count = -1
        
        if line.startswith('ATOM'):
            count = count + 1
            # Extract the fields from the line
            # If you distort the pdb format, like add too many spaces between two columns, this script also help since it can separate cotents,
            # but not read file based on the specific column numbers.
            split_line = line.split()

            # Read and output lines properly with no chain ID column
            if split_line[3] in spec:
                atom_type = split_line[0]
                # atom_number = int(split_line[1])
                atom_number = count
                atom_name = split_line[2]
                residue_name = split_line[3]
                residue_number = int(split_line[4])
                x_coord = float(split_line[5])
                y_coord = float(split_line[6])
                z_coord = float(split_line[7])
                occupancy = float(split_line[8])
                temp_factor = float(split_line[9])
                element = split_line[10]                

                output_file.write('{:<6s}{:>5d}{:1s}{:^4s}{:1s}{:<5s}{:>4}{:>12.3f}{:>8.3f}{:>8.3f}{:>6.2f}{:>6.2f}{:10s}{:>2s}\n'.format(
                atom_type, atom_number, '', atom_name, '', residue_name, residue_number, x_coord, y_coord, z_coord, occupancy, temp_factor,'', element))
            
            # Read and output lines properly with chain ID column
            else:
                atom_type = split_line[0]
                # atom_number = int(split_line[1])
                atom_number = count
                atom_name = split_line[2]
                residue_name = split_line[3]
                chain_id = split_line[4]
                residue_number = int(split_line[5])
                x_coord = float(split_line[6])
                y_coord = float(split_line[7])
                z_coord = float(split_line[8])
                occupancy = float(split_line[9])
                temp_factor = float(split_line[10])
                element = split_line[11]
            # Write the fields to the output file in the correct format
                output_file.write('{:<6s}{:>5d}{:1s}{:^4s}{:>4s}{:>2s}{:>4}{:>12.3f}{:>8.3f}{:>8.3f}{:>6.2f}{:>6.2f}{:10s}{:>2s}\n'.format(
                atom_type, atom_number, '', atom_name, residue_name, chain_id, residue_number, x_coord, y_coord, z_coord, occupancy, temp_factor,'', element))



        else:
            # If the line is not an ATOM or HETATM record, just write it to the output file as-is
            output_file.write(line)
