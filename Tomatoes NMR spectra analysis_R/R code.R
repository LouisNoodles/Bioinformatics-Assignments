library(ggfortify)
library(nFactors)
library(factoextra)
library(ropls)
library(tree)
library(randomForest)
library(ggplot2)


setwd("D:/KU Leuven/Third semester/Applied Multivariate Statistical Analysis/Assignment")

NMR<-read.table("Jiangli_Gui.csv",header=T,sep=',',na.strings="NA")

names(NMR)
names(NMR)[names(NMR) == "﻿Samplecode"] <- "Samplecode"
names(NMR)

attach(NMR)
dim(NMR)

NMR.mat <- NMR[,-c(1:3)]
#pairs(NMR.mat)

NMR.pca <- prcomp(NMR.mat, scale = FALSE)
plot(NMR.pca)
NMR.pca <- prcomp(NMR.mat, scale = TRUE)
plot(NMR.pca)
plot(NMR.pca, type = "l")
abline(h=1,lty='dashed') 

autoplot(NMR.pca, data = NMR, colour = 'Condition')
autoplot(NMR.pca, data = NMR, colour = 'Stage')

# Determine Number of Factors to Extract
eigenvalues <- eigen(cor(NMR.mat)) # get eigenvalues
aparallel <- parallel(subject=nrow(NMR.mat),var=ncol(NMR.mat),
               rep=100,cent=.05)
scree <- nScree(x=eigenvalues$values, aparallel=aparallel$eigen$qevpea)
plotnScree(scree)

NMR.fa <- factanal(NMR.mat, 4, rotation = "varimax", lower = 0.01, scores = "regression")
NMR.fa
autoplot(NMR.fa, data = NMR, colour = "Condition")
autoplot(NMR.fa, data = NMR, colour = "Stage")
NMR.fa$uniquenesses
apply(NMR.fa$loadings^2,1,sum) 

# try unsupervised method to cluster 
fviz_nbclust(NMR.mat, kmeans, method = "wss")

set.seed(100)
NMR.km.two <- kmeans(NMR.mat, 2, nstart = 5)
NMR.km.two
fviz_cluster(NMR.km.two, NMR.mat)
#autoplot(NMR.pca, data = NMR, colour = 'Condition') # Comparison
NMR.km.two$cluster[NMR.km.two$cluster == "1"] <- "Control"
NMR.km.two$cluster[NMR.km.two$cluster == "2"] <- "Shadow"
pred_clusters <- NMR.km.two$cluster
mis_rate_two <- mean(NMR[,2] != pred_clusters)
mis_rate_two #0.4761905

set.seed(100)
NMR.km.four <- kmeans(NMR.mat, 4, nstart = 5)
NMR.km.four
fviz_cluster(NMR.km.four, NMR.mat)
#autoplot(NMR.pca, data = NMR, colour = 'Stage') # Comparison
NMR.km.four$cluster[NMR.km.four$cluster == "1"] <- "J55"
NMR.km.four$cluster[NMR.km.four$cluster == "2"] <- "J08"
NMR.km.four$cluster[NMR.km.four$cluster == "3"] <- "J28"
NMR.km.four$cluster[NMR.km.four$cluster == "4"] <- "J15"
pred_clusters <- NMR.km.four$cluster
mis_rate <- mean(NMR[,3] != pred_clusters)
mis_rate #0.2142857

set.seed(100)
NMR.km.eight <- kmeans(NMR.mat, 8, nstart = 5)
NMR.km.eight
fviz_cluster(NMR.km.eight, NMR.mat)

# try supervised method
# treatment <- NMR[ ,2]
stage <- NMR[ ,3]

# NMR.oplsda <- opls(NMR.mat, treatment, predI = 1, orthoI = NA)
# Error: No model was built because the predictive component 
#        was not significant

# NMR.plsda <- opls(NMR.mat, stage)
NMR.plsda.cv <- opls(NMR.mat, stage, crossvalI = 40)
# NMR.pca.new <- opls(NMR.mat)

### tree based method to find feature variables
NMR.stage <- NMR[ ,-(1:2)]
NMR.stage$Stage <- as.factor(NMR.stage$Stage)

NMR.tree <- tree(Stage~., data = NMR.stage)
summary(NMR.tree)
plot(NMR.tree)
text(NMR.tree)
# "B1_8600" "B1_0442"

set.seed(100)
NMR.tree.cv <- cv.tree(NMR.tree,FUN=prune.misclass )
NMR.tree.cv #4
NMR.tree.prune <- prune.misclass (NMR.tree,best=4)
plot(NMR.tree.prune)
text(NMR.tree.prune)

NMR.rf =randomForest(Stage~.,data= NMR.stage, mtry=38,importance =TRUE, ntree = 100)
features <- importance(NMR.rf)

subset(features[ ,1],features[ ,1]>3)
subset(features[ ,2],features[ ,2]>3)
subset(features[ ,3],features[ ,3]>3)
subset(features[ ,4],features[ ,4]>3)

varImpPlot(NMR.rf)

# "B1_8600" "B1_0442" "B9_1275"

B1_0442 <- aggregate(B1_0442 ~ Stage, data = NMR, mean)
B1_8600 <- aggregate(B1_8600 ~ Stage, data = NMR, mean)
B9_1275 <- aggregate(B9_1275 ~ Stage, data = NMR, mean)

ggplot(data = B1_0442, aes(x = Stage, y = B1_0442)) +
  geom_bar(stat = 'identity', fill='blue') +
  geom_line()

ggplot(data = B1_8600, aes(x = Stage, y = B1_8600)) +
  geom_bar(stat = 'identity', fill='blue') +
  geom_line()

ggplot(data = B9_1275, aes(x = Stage, y = B9_1275)) +
  geom_bar(stat = 'identity', fill='blue') +
  geom_line()
