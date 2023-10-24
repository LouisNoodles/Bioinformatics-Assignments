getwd()
setwd("D:/KU Leuven/Thesis/Thesis writing/Results")


rmsd_helix_analysis <- function(file_path_AA, file_path_XX,state){
  s <- state
  helix_AA <- readxl::read_excel(file_path_AA)
  helix_XX <- readxl::read_excel(file_path_XX)
  plot(rmsd~time, data = helix_AA,xlim=c(0,1450),ylim=c(0,0.3), col="blue", type = 'l')
  helix_XX$time <- helix_XX$time + 450
  lines(rmsd~time, data = helix_XX, col="blue", type = 'l', add = TRUE)
}

helix_AA <- "RMSD/Helix/AA.xlsx"
helix_CC <- "RMSD/Helix/CC.xlsx"
rmsd_helix_analysis(helix_AA,helix_CC,"CC")
helix_FF <- "RMSD/Helix/FF.xlsx"
rmsd_helix_analysis(helix_AA,helix_FF,"FF")
helix_FF_re <- "RMSD/Helix/FF_re.xlsx"
rmsd_helix_analysis(helix_AA,helix_FF_re,"FF'")
helix_EE <- "RMSD/Helix/EE.xlsx"
rmsd_helix_analysis(helix_AA,helix_EE,"EE")
helix_EE_re <- "RMSD/Helix/EE_re.xlsx"
rmsd_helix_analysis(helix_AA,helix_EE_re,"EE'")


# RMSD analysis
rmsd_analysis <- function(file_path_XX,state){
  s <- state
  dis_XX <- readxl::read_excel(file_path_XX)
  dis_XX$rmsd <- dis_XX$rmsd * 10
  plot(rmsd~time, data = dis_XX,
       col="blue", type = 'l', xlab = "Time (ns)", 
       ylab = "RMSD (??)", main = "",
       cex.lab = 1.3, cex.axis = 1.1)
  
  title(main = paste("RMSD over time of", s),
        line = 1, cex.main = 1.2)
  mtext("Backbone", side = 3, line = 0)
}
# RMSD analysis
rmsd_AA <- "RMSD/AA.xlsx"
rmsd_CC <- "RMSD/CC.xlsx"
rmsd_EE <- "RMSD/EE.xlsx"
rmsd_EE_re <- "RMSD/EE_re.xlsx"
rmsd_FF <- "RMSD/FF.xlsx"
rmsd_FF_re <- "RMSD/FF_re.xlsx"

rmsd_analysis(rmsd_AA, "His,Cys bound(AA)")
rmsd_analysis(rmsd_CC, "NO bound(CC)")
rmsd_analysis(rmsd_EE, "His,CO bound(EE)")
rmsd_analysis(rmsd_EE_re,"His,CO bound(EE')")
rmsd_analysis(rmsd_FF, "His,NO bound(FF)")
rmsd_analysis(rmsd_FF_re,"His,NO bound(FF')")

# RMSF analysis
rmsf_analysis <- function(file_path_AA,file_path_XX,state,abbreviation){
  s <- state
  ab <- abbreviation
  rmsf_AA <- readxl::read_excel(file_path_AA)
  rmsf_XX <- readxl::read_excel(file_path_XX)
  rmsf_AA$rmsf_a <- rmsf_AA$rmsf_a * 10
  rmsf_AA$rmsf_b <- rmsf_AA$rmsf_b * 10
  rmsf_XX$rmsf_a <- rmsf_XX$rmsf_a * 10
  rmsf_XX$rmsf_b <- rmsf_XX$rmsf_b * 10
  
  plot(rmsf_a~resid, data = rmsf_XX, 
       xlab = "Residue No.", 
       ylim = c(min(rmsf_AA$rmsf_a,rmsf_AA$rmsf_b,
                    rmsf_XX$rmsf_a,rmsf_XX$rmsf_b),
                max(rmsf_AA$rmsf_a,rmsf_AA$rmsf_b,
                    rmsf_XX$rmsf_a,rmsf_XX$rmsf_b)),
       ylab = "RMSF (??)", main = "",
       cex.lab = 1.3, cex.axis = 1.1,col="blue", type = 'l')
  lines(rmsf_b~resid, data = rmsf_XX, col="red", type = 'l')
  lines(rmsf_a~resid, data = rmsf_AA, col="light blue",
        type = 'l')
  lines(rmsf_b~resid, data = rmsf_AA, col="lightsalmon",
        type = 'l')
  title(main = paste("RMSF of", s,ab),
        line = 1, cex.main = 1.2)
  mtext("Backbone", side = 3, line = 0)
  
  legend("topleft", legend = c(paste(s,"(a)"),
                           paste(s,"(b)"),
                           "His,Cys bound (a)", "His,Cys bound (b)"), 
         col = c("blue", "red","light blue", "lightsalmon"),
         lwd = 2, lty = c("solid","solid","solid", "solid"),
         pch = 16, cex = 0.6, text.width = 0.06,inset = 0.15,
         bty = "n")
  # readline()
  # rmsf_XX$rmsf_a <- rmsf_XX$rmsf_a-rmsf_AA$rmsf_a
  # rmsf_XX$rmsf_b <- rmsf_XX$rmsf_b-rmsf_AA$rmsf_b
  # plot(rmsf_a~resid, data = rmsf_XX, 
  #      xlab = "Residue No.", 
  #      ylab = "RMSF (??)", main = "",
  #      ylim = c(1,
  #               max(rmsf_AA$rmsf_a,rmsf_AA$rmsf_b,
  #                   rmsf_XX$rmsf_a,rmsf_XX$rmsf_b)),
  #      cex.lab = 1.3, cex.axis = 1.1,col="blue", type = 'l')
  # lines(rmsf_b~resid, data = rmsf_XX, col="red", type = 'l')
  # title(main = paste("Subtract",s,"from His,Cys bound"),
  #       line = 1, cex.main = 1.2)
  # mtext("Cutoff = 1 ??", side = 3, line = 0)
  # 
  # legend("topright", legend = c("Chain a", "Chain b"), 
  #        col = c("blue", "red"),
  #        lwd = 2, lty = c("solid", "solid"),
  #        pch = 16, cex = 0.55, text.width = 0.06,inset = 0.12,
  #        bty = "n")
}
rmsf_AA <- "RMSF/AA.xlsx"
rmsf_CC <- "RMSF/CC.xlsx"
rmsf_analysis(rmsf_AA, rmsf_CC, "NO bound","(CC)")
rmsf_EE <- "RMSF/EE.xlsx"
rmsf_analysis(rmsf_AA, rmsf_EE, "His,CO bound","(EE)")
rmsf_EE_re <- "RMSF/EE_re.xlsx"
rmsf_analysis(rmsf_AA, rmsf_EE_re, "His,CO bound", "(EE')")
rmsf_FF <- "RMSF/FF.xlsx"
rmsf_analysis(rmsf_AA, rmsf_FF, "His,NO bound", "(FF)")
rmsf_FF_re <- "RMSF/FF_re.xlsx"
rmsf_analysis(rmsf_AA, rmsf_FF_re, "His,NO bound", "(FF')")


# distance analysis
dist_AA_analysis <- function(file_path_AA) {
  # Load the data from the AA file
  dis_AA <- readxl::read_excel(file_path_AA)
  
  dis_AA <- dis_AA[, -1] * 10
  # Load the data from the FF file
  plot(density(dis_AA$a1), col = "blue",xlab = "Distance (??)", 
       ylab = "Density", main = "", lwd = 2,
       cex.lab = 1.3, cex.axis = 1.1)
  title(main = "Cys52 and Arg266 of AA",
        line = 1, cex.main = 1.2)
  lines(density(dis_AA$b1), col = "red", lwd = 2)
  legend("topright", legend = c("h1 (A)", "h1 (B)"), 
         col = c("blue", "red"),
         lwd = 2, lty = c("solid", "solid"),
         pch = 16, cex = 0.8,bty = "n")
  # type "Enter" in Console to continue
  readline()
  
  plot(density(dis_AA$a2), col = "blue",xlab = "Distance (??)",
       xlim = c(2.0,3.5),ylim =c(0,4.5), ylab = "Density",
       main = "", lwd = 2,
       cex.lab = 1.3, cex.axis = 1.1)
  title(main = "Thr257, Thr260 and PLP of AA",
        line = 1, cex.main = 1.2)
  lines(density(dis_AA$a3), col = "blue", lwd = 2, lty = "twodash")
  lines(density(dis_AA$b2), col = "red", lwd = 2)
  lines(density(dis_AA$b3), col = "red", lwd = 2, lty = "twodash")
  legend("topright", legend = c("h2 (A)", "h3 (A)", "h2 (B)", "h3 (B)"), 
         col = c("blue", "blue", "red", "red"),
         lwd = 2, lty = c("solid", "twodash", "solid", "twodash"),
         pch = 16, cex = 0.8,bty = "n")
  readline()
  
  plot(density(dis_AA$a4), col = "blue",xlab = "Distance (??)",
       ylab = "Density", main = "", 
       xlim = c(2.5, 5),
       lwd = 2,cex.lab = 1.3, cex.axis = 1.1)
  lines(density(dis_AA$b4), col = "red", lwd = 2)
  title(main = "Asn149 and PLP of AA",
        line = 1, cex.main = 1.2)
  legend("topright", legend = c("h4 (A)", "h4 (B)"), 
         col = c("blue", "red"),
         lwd = 2, lty = c("solid", "solid"),
         pch = 16, cex = 0.8,bty = "n")
  readline()
  
  plot(density(dis_AA$b5), col = "blue",xlab = "Distance (??)",
       ylab = "Density", main = "", 
       xlim = c(2.5, 3.5),
       lwd = 2,cex.lab = 1.3, cex.axis = 1.1)
  title(main = "Ser349 and PLP of AA",
        line = 1, cex.main = 1.2)
  lines(density(dis_AA$b5), col = "red", lwd = 2,lty = "twodash")
  legend("topright", legend = c("h5 (A)", "h5 (B)"), 
         col = c("blue", "red"),
         lwd = 2, lty = c("solid", "twodash"),
         pch = 16, cex = 0.8,bty = "n")
  readline()
  
  plot(density(dis_AA$a6, bw = 0.1), col = "blue",xlab = "Distance (??)",
       ylab = "Density", main = "", 
       ylim=c(0,2),
       lwd = 2,cex.lab = 1.3, cex.axis = 1.1)
  title(main = "Heme and ¦Á-helix 8 of AA",
        line = 1, cex.main = 1.2)
  lines(density(dis_AA$b6), col = "red", lwd = 2,)
  legend("topright", legend = c("h5 (A)", "h5 (B)"), 
         col = c("blue", "red"),
         lwd = 2, lty = c("solid", "solid"),
         pch = 16, cex = 0.8,
         bty = "n")
}
dist_XX_analysis <- function(file_path_XX,s){
  dis_XX <- readxl::read_excel(file_path_XX)
  s <- s
  dis_XX$time <- dis_XX$time / 1000
  dis_XX[, -1] <- dis_XX[, -1] * 10
  plot(dis_XX$a1~time, data = dis_XX, col="blue", type = 'l',
       xlab = "Time (ns)", 
       ylab = "Distance (??)", main = "",
       ylim = c(min(dis_XX$a1, dis_XX$b1), max(dis_XX$a1, dis_XX$b1)),
       cex.lab = 1.3, cex.axis = 1.1)
  title(main = paste("Cys52 and Arg266 of",s),
        line = 1, cex.main = 1.2)
  lines(dis_XX$b1~time, data = dis_XX, col=rgb(1, 0, 0, alpha = 0.7), type = 'l')
  mtext("Chain a in Blue, Chain b in Red", side = 3, line = 0, cex = 0.7)
  # type "Enter" in Console to continue
  readline()
  
  density_a2 <- density(dis_XX$a2)
  density_b2 <- density(dis_XX$b2)
  density_a3 <- density(dis_XX$a3)
  density_b3 <- density(dis_XX$b3)
  
  # EE,FF
  # xlim = c(2,3.5),
  # xlim = c(min(density_a2$x,density_b2$x,
  #               density_a3$x,density_b3$x),
  #               max(density_a2$x,density_b2$x,
  #               density_a3$x,density_b3$x)),

  plot(density(dis_XX$a2), col = "blue",xlab = "Distance (??)",
       xlim = c(min(density_a2$x,density_b2$x,
                                  density_a3$x,density_b3$x),
                                  max(density_a2$x,density_b2$x,
                                  density_a3$x,density_b3$x)),
       ylim =c(0,max(density_a2$y,density_b2$y,
                     density_a3$y,density_b3$y)), 
       ylab = "Density",
       main = "", lwd = 2,
       cex.lab = 1.3, cex.axis = 1.1)
  
  
  title(main = paste("Thr257, Thr260 and PLP of", s),
        line = 1, cex.main = 1)
  lines(density(dis_XX$a3), col = "blue", lwd = 2, lty = "twodash")
  lines(density(dis_XX$b2), col = "red", lwd = 2)
  lines(density(dis_XX$b3), col = "red", lwd = 2, lty = "twodash")
  legend("topright", legend = c("h2 (a)", "h3 (a)", "h2 (b)", "h3 (b)"), 
         col = c("blue", "blue", "red", "red"),
         lwd = 2, lty = c("solid", "twodash", "solid", "twodash"),
         pch = 16,cex = 0.8,bty = "n")
  readline()
  
  plot(dis_XX$a4~time, data = dis_XX, col="blue", type = 'l',
       xlab = "Time (ns)", 
       ylab = "Distance (??)", main = "",
       ylim = c(min(dis_XX$a4, dis_XX$b4), max(dis_XX$a4, dis_XX$b4)),
       cex.lab = 1.3, cex.axis = 1.1)
  lines(dis_XX$b4~time, data = dis_XX, col=rgb(1, 0, 0, alpha = 0.7), type = 'l')
  title(main = paste("Asn149 and PLP of",s),
        line = 1, cex.main = 1.2)
  mtext("Chain a in Blue, Chain b in Red", side = 3, line = 0, cex = 0.7)
  readline()
  
  plot(dis_XX$a5~time, data = dis_XX, col="blue", type = 'l',
       xlab = "Time (ns)", 
       ylab = "Distance (??)", main = "",
       ylim = c(min(dis_XX$a5, dis_XX$b5), max(dis_XX$a5, dis_XX$b5)),
       cex.lab = 1.3, cex.axis = 1.1)
  lines(dis_XX$b5~time, data = dis_XX, 
        col = rgb(1, 0, 0, alpha = 0.7), 
        type = 'l')
  title(main = paste("Ser349 and PLP of",s),
        line = 1, cex.main = 1.2)
  mtext("Chain a in Blue, Chain b in Red", side = 3, line = 0, cex = 0.7)
  readline()
  
  plot(dis_XX$a6~time, data = dis_XX, col="blue", type = 'l',
       xlab = "Time (ns)", 
       ylab = "Distance (??)", main = "",
       ylim = c(min(dis_XX$a6, dis_XX$b6), max(dis_XX$a6, dis_XX$b6)),
       cex.lab = 1.3, cex.axis = 1.1)
  title(main = paste("Heme and ¦Á-helix 8 of", s),
        line = 1, cex.main = 1.1)
  lines(dis_XX$b6~time, data = dis_XX, 
        col = rgb(1, 0, 0, alpha = 0.7), 
        type = 'l')
  mtext("Chain a in Blue, Chain b in Red", side = 3, line = 0, cex = 0.7)
}

# distance analysis
# dist_AA <- "Distance/dis_AA.xlsx"
# dist_AA_analysis(dist_AA)

dist_AA <- "Distance/dis_AA.xlsx"
dist_XX_analysis(dist_AA,"His,Cys bound(AA)")
dist_FF <- "Distance/dis_FF.xlsx"
dist_XX_analysis(dist_FF,"His,NO bound(FF)")
dist_FF_re <- "Distance/dis_FF_re.xlsx"
dist_XX_analysis(dist_FF_re,"His,NO bound(FF')")
dist_EE <- "Distance/dis_EE.xlsx"
dist_XX_analysis(dist_EE,"His,CO bound(EE)")
dist_EE_re <- "Distance/dis_EE_re.xlsx"
dist_XX_analysis(dist_EE_re,"His,CO bound(EE')")
dist_CC <- "Distance/dis_CC.xlsx"
dist_XX_analysis(dist_CC,"NO bound(CC)")


# rotation analysis
perform_rotation_analysis <- function(file_path_AA, file_path_FF,state,r) {
  file_name <- state
  r <- r
  # Load the data from the AA file
  rotation_AA <- readxl::read_excel(file_path_AA)
  # Load the data from the FF file
  rotation_FF <- readxl::read_excel(file_path_FF)
  # Divide time by 1000 for both datasets
  rotation_AA$time <- rotation_AA$time/1000
  rotation_FF$time <- rotation_FF$time/1000
  
  # rotation_AA$angle_B <- 180 - rotation_AA$angle_B
  # rotation_FF$angle_A <- 180 - rotation_FF$angle_A
  # rotation_FF$angle_B <- 180 - rotation_FF$angle_B
  
  # CC
  # rotation_FF <- subset(rotation_FF, angle_A >= 120 & angle_B <= 60)
  
  par(mfrow = c(2, 2))
  # Plot angle_A vs. time for AA
  plot(rotation_AA$angle_A ~ rotation_AA$time,
       data = rotation_AA, col = "blue", type = "l",
       xlab = "Time (ns)", ylab = "Angle",
       cex.lab = 1.5, cex.axis = 1.2)
  title(main = paste("Angle",r, "over time of His, Cys bound(AA) in chain a"),
        line = 1, cex.main = 1.2)
  # Perform linear regression for angle_A vs. time for AA
  lm_model_AA_A <- lm(angle_A ~ time, data = rotation_AA)
  # Add the regression line to the plot
  abline(lm_model_AA_A, lwd = 2, col = "purple" )
  
  # Plot density of angle_A for AA
  density_AA_A <- density(rotation_AA$angle_A)
  plot(density_AA_A, col = "blue",xlab = "Angle",
       ylab = "Density", cex.lab = 1.5, cex.axis = 1.2,
       main = "", lwd = 2)
  title(main = paste("Angle",r, "distribution of His, Cys bound(AA) in chain a"),
        line = 1, cex.main = 1.2)

  # Plot angle_B vs. time for AA
  plot(rotation_AA$angle_B ~ rotation_AA$time,
       data = rotation_AA, col = "blue", type = "l",
       xlab = "Time (ns)", ylab = "Angle",
       cex.lab = 1.5, cex.axis = 1.2)
  title(main = paste("Angle",r, "over time of His, Cys bound(AA) in chain b"),
        line = 1, cex.main = 1.2)
  # Perform linear regression for angle_B vs. time for AA
  lm_model_AA_B <- lm(angle_B ~ time, data = rotation_AA)
  # Add the regression line to the plot
  abline(lm_model_AA_B,lwd = 2, col = "purple")

  # Plot density of angle_B for AA
  density_AA_B <- density(rotation_AA$angle_B)
  plot(density_AA_B, col = "blue", xlab = "Angle",
       ylab = "Density",cex.lab = 1.5, cex.axis = 1.2,
       main = "", lwd = 2)
  title(main = paste("Angle",r, "distribution of His, Cys bound(AA) in chain b"),
        line = 1, cex.main = 1.2)

  # Prompt for user input before proceeding to the next plot
  cat("Press Enter to continue...")
  readline()
  par(mfrow = c(2, 2))
  # Plot angle_A vs. time for FF
  plot(rotation_FF$angle_A ~ rotation_FF$time, 
       data = rotation_FF, col = "blue", type = "l",
       xlab = "Time (ns)", ylab = "Angle",
       cex.lab = 1.5, cex.axis = 1.2)
  title(main = paste("Angle",r,"over time of", file_name, "in chain a"),
        line = 1, cex.main = 1.2)
  # Perform linear regression for angle_A vs. time for FF
  lm_model_FF_A <- lm(angle_A ~ time, data = rotation_FF)
  # Add the regression line to the plot
  abline(lm_model_FF_A, lwd = 2, col = "purple")
  
  
  # Plot density of angle_A for FF with overlaid density of angle_A for AA
  density_FF_A <- density(rotation_FF$angle_A)
  plot(density_FF_A, col = "red", xlab = "Angle",
       ylab = "Density", cex.lab = 1.5, cex.axis = 1.2,
       main = "", lwd = 2,
       xlim = c(min(density_AA_A$x,density_FF_A$x),
                max(density_AA_A$x,density_FF_A$x)),
       ylim = c(min(density_AA_A$y,density_FF_A$y),
                max(density_AA_A$y,density_FF_A$y)))
  lines(density_AA_A, col = "blue",lwd = 2)
  title(main = paste("Angle",r,"distribution of", file_name, "in chain a"),
        line = 1, lwd = 2, cex.main = 1.2)
  mtext(paste("His, Cys bound(AA) in Blue,", file_name, "in Red"),
        side = 3, line = 0, cex = 0.7)
  
  # Plot angle_B vs. time for FF
  plot(rotation_FF$angle_B ~ rotation_FF$time, 
       data = rotation_FF, col = "blue", type = "l",
       xlab = "Time (ns)", ylab = "Angle",
       cex.lab = 1.5, cex.axis = 1.2)
  title(main = paste("Angle",r,"over time of", file_name, "in chain b"),
        line = 1, cex.main = 1.2)
  # Perform linear regression for angle_B vs. time for FF
  lm_model_FF_B <- lm(angle_B ~ time, data = rotation_FF)
  # Add the regression line to the plot
  abline(lm_model_FF_B, lwd = 2, col = "purple")
  
  # Plot density of angle_B for FF with overlaid density of angle_B for AA
  density_FF_B <- density(rotation_FF$angle_B)
  plot(density_FF_B, col = "red", xlab = "Angle",
       ylab = "Density", cex.lab = 1.5, cex.axis = 1.2,
       main = "", lwd = 2,
       ylim = c(min(density_AA_B$y,density_FF_B$y),
                max(density_AA_B$y,density_FF_B$y)),
       xlim = c(min(density_AA_B$x,density_FF_B$x),
                max(density_AA_B$x,density_FF_B$x)))
  lines(density_AA_B, col = "blue",lwd = 2)
  title(main = paste("Angle",r,"distribution of", file_name, "in chain b"),
        line = 1, cex.main = 1.2)
  mtext(paste("His, Cys bound(AA) in Blue,", file_name, "in Red"),
        side = 3, line = 0, cex = 0.7)
  # Reset the layout to the default
  par(mfrow = c(1, 1))
}

# Rotation analysis
### 266Arg
rotation_AA_Arg <- "Distance/Rotation/Arg/rotation_AA.xlsx"
rotation_FF <- "Distance/Rotation/Arg/rotation_FF.xlsx"
perform_rotation_analysis(rotation_AA_Arg, rotation_FF,"His, NO bound(FF)", "r1")
rotation_FF_re <- "Distance/Rotation/Arg/rotation_FF_re.xlsx"
perform_rotation_analysis(rotation_AA_Arg, rotation_FF_re,"His, NO bound(FF')", "r1")
rotation_EE <- "Distance/Rotation/Arg/rotation_EE.xlsx"
perform_rotation_analysis(rotation_AA_Arg, rotation_EE,"His, CO bound(EE)", "r1")
rotation_EE_re <- "Distance/Rotation/Arg/rotation_EE_re.xlsx"
perform_rotation_analysis(rotation_AA_Arg, rotation_EE_re,"His, CO bound(EE')", "r1")
rotation_CC <- "Distance/Rotation/Arg/rotation_CC.xlsx"
perform_rotation_analysis(rotation_AA_Arg, rotation_CC,"NO bound(CC)", "r1")

rotation_AA_Arg_z <- "Distance/Rotation/Arg/rotation_AA_z.xlsx"
rotation_FF_z <- "Distance/Rotation/Arg/rotation_FF_z.xlsx"
rotation_FF_re_z <- "Distance/Rotation/Arg/rotation_FF_re_z.xlsx"
rotation_EE_z <- "Distance/Rotation/Arg/rotation_EE_z.xlsx"
rotation_EE_re_z <- "Distance/Rotation/Arg/rotation_EE_re_z.xlsx"
perform_rotation_analysis(rotation_AA_Arg_z, rotation_FF_z,"His, NO bound(FF)_z", "r1")
perform_rotation_analysis(rotation_AA_Arg_z, rotation_FF_re_z,"His, NO bound(FF')_z", "r1")
perform_rotation_analysis(rotation_AA_Arg_z, rotation_EE_z,"His, CO bound(EE)_z", "r1")
perform_rotation_analysis(rotation_AA_Arg_z, rotation_EE_re_z,"His, CO bound(EE')_z", "r1")

rotation_CC_z <- "Distance/Rotation/Arg/rotation_CC_z.xlsx"
perform_rotation_analysis(rotation_AA_Arg_z, rotation_CC_z,"NO bound(CC)","r1")

### 260 Thr
rotation_AA_Thr <- "Distance/Rotation/Thr/rotation_AA.xlsx"
rotation_FF <- "Distance/Rotation/Thr/rotation_FF.xlsx"
perform_rotation_analysis(rotation_AA_Thr, rotation_FF,"His, NO bound(FF)","r2")
rotation_FF_re <- "Distance/Rotation/Thr/rotation_FF_re.xlsx"
perform_rotation_analysis(rotation_AA_Thr, rotation_FF_re,"His, NO bound(FF')","r2")
rotation_EE <- "Distance/Rotation/Thr/rotation_EE.xlsx"
perform_rotation_analysis(rotation_AA_Thr, rotation_EE,"His, CO bound(EE)","r2")
rotation_EE_re <- "Distance/Rotation/Thr/rotation_EE_re.xlsx"
perform_rotation_analysis(rotation_AA_Thr, rotation_EE_re,"His, CO bound(EE')","r2")
rotation_CC <- "Distance/Rotation/Thr/rotation_CC.xlsx"
perform_rotation_analysis(rotation_AA_Thr, rotation_CC,"NO bound(CC)","r2")

rotation_AA_Thr_z <- "Distance/Rotation/Thr/rotation_AA_z.xlsx"
rotation_FF_z <- "Distance/Rotation/Thr/rotation_FF_z.xlsx"
rotation_FF_re_z <- "Distance/Rotation/Thr/rotation_FF_re_z.xlsx"
rotation_EE_z <- "Distance/Rotation/Thr/rotation_EE_z.xlsx"
rotation_EE_re_z <- "Distance/Rotation/Thr/rotation_EE_re_z.xlsx"
perform_rotation_analysis(rotation_AA_Thr_z, rotation_FF_z,"His, NO bound(FF)_z","r2")
perform_rotation_analysis(rotation_AA_Thr_z, rotation_FF_re_z,"His, NO bound(FF')_z","r2")
perform_rotation_analysis(rotation_AA_Thr_z, rotation_EE_z,"His, CO bound(EE)_z","r2")
perform_rotation_analysis(rotation_AA_Thr_z, rotation_EE_re_z,"His, CO bound(EE')_z","r2")
rotation_CC_z <- "Distance/Rotation/Thr/rotation_CC_z.xlsx"
perform_rotation_analysis(rotation_AA_Thr_z, rotation_CC_z,"NO bound(CC)","r2")
