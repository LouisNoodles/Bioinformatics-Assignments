# -*- coding: utf-8 -*-
import os
import networkx as nx
from networkx.algorithms import community
import matplotlib.pyplot as plt
import csv

print(os.getcwd())
os.chdir("D:/KU Leuven/Third semester/Comparative and Regulatory Genomics/Comparative genomics/Assignment")

def getPairs(filename):
    rows = []
    with open(filename, 'r') as f:
        for line in f:
            rows.append(line.strip())
    
    # print("this is the ", filename)
    # print("length of original file", len(rows))
    
    pairs = {}
    
    # Iterate over the rows of data
    for row in rows:
        # Split the row into columns
        cols = row.split()
        # Extract the values for the first and second columns and the bit score
        id1 = cols[0]
        id2 = cols[1]
        bitscore = float(cols[2])
        # Check if the unique ID is already in the dictionary
        if id1 in pairs:
            # If it is, check if the current bit score is higher
            if bitscore > pairs[id1][1]:
                # If it is, update the pair for the unique ID
                pairs[id1] = (id2, bitscore)
        else:
            # If the unique ID is not in the dictionary, add the current pair
            pairs[id1] = (id2, bitscore)
            
    print("pair number", len(pairs))
    # print(pairs)
    return pairs

def get_BBH(pair1, pair2):
    bbh = {}
    rb_ss_pairs = pair1
    ss_rb_pairs = pair2
    for pairA in rb_ss_pairs: 
        for pairB in ss_rb_pairs:
            if pairA == ss_rb_pairs[pairB][0]:
                if pairB == rb_ss_pairs[pairA][0]:
                    bbh[pairA] = pairB

    # print("the orthology identified by BBH", len(bbh))
    return bbh

def getInparalogs(filename):

    # Load the graph from a file
    G = nx.read_edgelist(filename)
    
    # Draw the graph using the spring layout
    pos = nx.spring_layout(G)
    nx.draw(G, pos, with_labels=True)
    
    # Show the plot
    plt.show()
    
    # Compute the clusters using the modularity maximization algorithm
    clusters = community.greedy_modularity_communities(G)
    
    inParalogs_groups = []
    
    # Transfer frozensets into normal sets
    for cluster in clusters:
        group = set(cluster)
        inParalogs_groups.append(group)
    
    
    # print(inParalogs_groups)
    return inParalogs_groups


def findCoOrthologs(inParalogs_groups, bbh):
    # Create a new dictionary to store the co-orthology information
    co_orthologs = {}
    
    # Iterate over the sets in the list
    for group in inParalogs_groups:
        # Iterate over the elements in the set
        for element in group:
            if element.startswith("ERL"):
                # Check if the element is a key in the bbh dictionary
                if element in bbh:
                    co_orthologs[element] = bbh[element]
                    # Iterate over the remaining elements in the group
                    for other_element in group:
                        if other_element != element:
                            co_orthologs[other_element] = bbh[element]
            elif element.startswith("EEP"):
                # Check if the element is a value in the bbh dictionary
                # Normally we used "ERL" as key and "EEP" as value,
                # While in this case, "EEP" are in-paralogs, so one "ERL"
                # can have multiple values, while is not capable for the
                # dictionary structure, so we reverse the key-value
                # relationship here
                for key, value in bbh.items():
                    if element == value:
                        co_orthologs[element] = key
                        # Iterate over the remaining elements in the group
                        for other_element in group:
                            if other_element != element:
                                co_orthologs[other_element] = key
    
    return co_orthologs

def output(filename, dictionary):
    # create a file "co_orthology.tsv" to store co-orthology information
    with open(filename, "w", newline="") as f:
        # create a csv writer object
        writer = csv.writer(f, delimiter="\t")
        # write the dictionary to the file, as rows
        for key, value in dictionary.items():
            writer.writerow([key, value])
    return 

rb_ss_pairs = getPairs('rb_ss.txt')
ss_rb_pairs = getPairs('ss_rb.txt')

bbh = get_BBH(rb_ss_pairs, ss_rb_pairs)

print(len(bbh))
# print(bbh['ERL96553.1'])
del bbh['ERL98362.1']
# print(bbh['ERL98677.1'])
del bbh['ERL98677.1']
# print(bbh['ERL99007.1'])
del bbh['ERL99007.1']
# print(bbh['ERL98203.1'])
del bbh['ERL98203.1']

output("BBH.tsv", bbh)


rb_inParalogs = getInparalogs('rb_inP.txt')
#print(len(rb_inParalogs))
# print(rb_inParalogs)
ss_inParalogs = getInparalogs('ss_inP.txt')
# print(ss_inParalogs)



rb_co_orthologs = findCoOrthologs(rb_inParalogs, bbh)
# print(len(rb_co_orthologs))
# print(rb_co_orthologs)
ss_co_orthologs = findCoOrthologs(ss_inParalogs, bbh)
# print(len(ss_co_orthologs))
# print(ss_co_orthologs)


co_orthologs = rb_co_orthologs.copy()
co_orthologs.update(ss_co_orthologs)

for key, value in co_orthologs.items():
    if key in rb_co_orthologs and key in ss_co_orthologs:
        # Key exists in both dictionaries, check if the values are the same
        if rb_co_orthologs[key] == ss_co_orthologs[key]:
            print(f'{key} has the same value in both dictionaries')
        else:
            print(f'{key} has different values in the two dictionaries')

# print(co_orthologs)  
# print(len(co_orthologs))

output("co_orthology.tsv", co_orthologs)

co_orthologs_groups = getInparalogs("co_orthology.tsv")

#print(len(co_orthologs_groups))
#print(co_orthologs_groups)

# {'ERL98677.1', 'ERL96553.1', 'ERL98362.1'; 'EEP27403.1', 'EEP27674.1'}
# {'ERL98203.1', 'ERL99007.1'; 'EEP28227.1', 'EEP28013.1'}
