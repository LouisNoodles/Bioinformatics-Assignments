# These are the only packages you are allowed import:
# import csv
# import numpy as np

# "pass" indicates an empty block of code, 
# remove it when filling in the functions.

def my_name():
    # Replace this with your full name.
    return "Jiangli Gui"

def parse_network(filename):
    file = open(filename)
    lines = []
    for line in file:
        line=line.strip('4.1867\n')
        lines.append(line) 
    file.close()
    # read the input file
    
    str_lines = " ".join(lines) 
    # from list to string to use split() later
    individual_ID = str_lines.split() 
    # all the IDs of a protein were put in a ''
    set_ID = set(individual_ID) 
    #uniqueness
    protein_list = []
    for i in set_ID:
        protein_list.append(i) 
        # from set to list
    protein_list = sorted(protein_list)
    # create the list of protein IDs
    
    column1 = individual_ID[0::2] # select the first column
    column2 = individual_ID[1::2] # select the second column
    
    group1 = list(zip(column1, column2)) # group the pair of two column
    group2 = list(zip(column2, column1))
    edges = set(group1 + group2) # put all of groups into a set
    return protein_list, edges


def build_laplacian(protein_list, edges):
    protein_list =  protein_list #a
    edges = edges #b
    # put the return result separately
    edges_list = list(edges) #c
        
    import numpy as np
    A = np.zeros((len(protein_list),len(protein_list)))
    for i in range(len(edges_list)):
        x,y = list(list(edges_list[i]))
        u = protein_list.index(x)
        v = protein_list.index(y)
        A[u][v] = 1
    # A is the adjacency matrix
    A = A.astype(int)
    #print(A)
    D = np.diag(A.sum(axis=1))
    #print(D)
    #D is the degree matrix
    laplacian = D - A
    #print(laplacian)
    return laplacian

def connected_components(laplacian):
    import numpy as np
    laplacian = laplacian
    eigenvalues = np.linalg.eigvals(laplacian)
    #print(eigenvalues)
    eigenvalues = eigenvalues.round(1)
    #eigenvalues_int = eigenvalues.astype(int)
    #print(eigenvalues_int)
    matrix_size = eigenvalues.size
    #print(matrix_size) #144
    Nonzero_number = np.count_nonzero(eigenvalues)
    #print(Nonzero_number) #120
    return matrix_size - Nonzero_number
        
def get_neighborhood(protein, edges):
    edges_list = list(edges) 
    ID = []
    for i in range(len(edges)):
        x,y = list(edges_list[i])
        ID.append(x)
        ID.append(y)
    allIDs = set(ID)
    protein_list = sorted(allIDs)

    import numpy as np
    A = np.zeros((len(protein_list),len(protein_list)))
    for i in range(len(edges_list)):
        x,y = list(edges_list[i])
        u = protein_list.index(x)
        v = protein_list.index(y)
        A[u][v] = 1
    # above all, construt adjacency matrix A for later use 
    
    protein_ID = protein
    r = protein_list.index(protein_ID)
    # get the specific ID of input protein
    neighborhoods = []
    for i in range(len(protein_list)):
        if A [r][i] == 1:
            neighborhood = protein_list[i]
            neighborhoods.append(neighborhood)
    # find all edges from the matrix A
    neighborhoods.append(protein)
    # add itself also
    neighborhoods = set(neighborhoods)
    return neighborhoods
    
def detect_cluster(protein, edges):
    
    setofneighborhoods = get_neighborhood(protein, edges)
    K = len(setofneighborhoods)
    
    group = [(x,y) for x in setofneighborhoods for y in setofneighborhoods]
    count = 0
    for i in range(len(group)):
        if group[i] in edges:
            count = count + 1
    E = count
    cluter_coefficient = E/(K*(K-1))
    return cluter_coefficient, K
    
def get_all_clusters(protein_list, edges):
    all_clusters = []
    
    for i in range(len(protein_list)):
        cc, k = detect_cluster(protein_list[i],edges)
        
        if k > 3:
            if cc >= 0.90:
                a = [protein_list[i]]
                b = [k]
                c = [cc]
                line = a+b+c
                groupresult = tuple(line)
                all_clusters.append(groupresult)
    
    def neighborhood_size(elem):
        return elem[1]
    
    all_clusters.sort(key=neighborhood_size)
    return all_clusters
    

# This magic if statement makes the code in the block only run when it is 
# not imported as a module. You can run your functions here.
if __name__ == "__main__":
    
    
    # Question 1
    #filename = 'example_network.tsv'
    filename = 'HT.Combined.PMID-14690591.144gene.688link.txt'
    protein_list, edges = parse_network(filename)
    #print(protein_list)
    #print(type(protein_list))
    #print(len(protein_list))
    #print(edges)
    
    # Question 2.1
    laplacian = build_laplacian(protein_list, edges)
    #print(laplacian)
    
    # Question 2.2
    cc_number = connected_components(laplacian)
    #print(cc_number)
    
    
    # Question 3.1
    get_neighborhood('YLR117C', edges)
    get_neighborhood('YJL081C', edges)
    
    
    # Question 3.2
    detect_cluster("YMR203W", edges)
    detect_cluster("YJL081C", edges)
    
    
    
    # Question 3.3
    get_all_clusters(protein_list, edges)

    
    
    