# -*- coding: utf-8 -*-
import os
import numpy as np
from Bio import AlignIO
import matplotlib.pyplot as plt
import math


print(os.getcwd())
os.chdir("C:/Users/ydlmtn/Desktop/CBS_alignment")

def read_File(filename,file_type):
    # Read the MSA file
    alignment = AlignIO.read(filename,file_type)
    
    # Get the number of sequences and the length of the alignment
    num_seqs = len(alignment)
    aln_len = alignment.get_alignment_length()
    
    # Create a matrix to store the data
    matrix = np.empty((num_seqs, aln_len), dtype=np.object)
    
    # Iterate over the rows and columns of the matrix
    for i, record in enumerate(alignment):
        for j in range(aln_len):
            matrix[i, j] = record.seq[j]
    
    
    # Print the matrix
    num_rows, num_cols = matrix.shape
    print(f'The matrix has {num_rows} rows and {num_cols} columns')
    # The matrix has 30 rows and 1425 columns
    return matrix

def get_Conservation(matrix):
    Simpson_Diversity = []
    Shannon_Entropy = []
    for i in range(len(matrix[0])):
        column = (matrix[:, i])
        # Count the number of occurrences of each category
        counts = {}
        for x in column:
            if x in counts:
                counts[x] += 1
            else:
                counts[x] = 1
        
        N = len(column)
        D = 1 - sum(counts[x] * (counts[x] - 1) / (N * (N - 1)) for x in counts)
        # H = -1 * sum(counts[x]/N * math.log2(counts[x]/N) for x in counts)
        Simpson_Diversity.append(D)
        # Shannon_Entropy.append(H)
    return Simpson_Diversity;

def get_Conservation_rate(list,threshold):
    count = 0
    for e in list:
        if e <= threshold:
            count += 1
    # print(count)
    conser_rate = count/len(list)
    # print(conser_rate)
    conser_rate = '%.2f%%' % (conser_rate * 100)
    return conser_rate


def visualization(sd,threshold):
    # plot 
    x = np.array(range(0,len(sd)))
    colors = ['red' if val == 0 else 'mediumorchid' if val == 0.4 else 'gray' for val in sd]

    plt.figure()
    plt.scatter(x, sd, marker='o', s=6, color=colors)
    plt.xlabel("resid", fontsize = 16)
    plt.ylabel("Simpson Diversity", fontsize = 16) 
    plt.tick_params(axis='both', labelsize=12) 
    plt.grid(True)
    plt.show()

    # legend_labels = ['Highly Conserved (<= 0.4)', 'Not Conserved (> 0.4)']
    # legend_colors = ['red', 'gray']
    # legend_elements = [plt.Line2D([0], [0], marker='o', color='w', markerfacecolor=color, markersize=8) 
    #                    for color in legend_colors]
    # plt.legend(legend_elements, legend_labels, loc='center left', bbox_to_anchor=(1, 0.5), 
    #            title='Legend', fontsize=12)

    conser_rate = get_Conservation_rate(sd, threshold)
    print("Consevation rate calculated by Simpson Diversity method", conser_rate)

    
    return 
    

matrix = read_File('all.fasta', 'fasta')
sd = get_Conservation(matrix)
visualization(sd,0.4)


