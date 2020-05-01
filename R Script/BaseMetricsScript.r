
library(foreign)
library(caret)
library(car)
library(nlme)
library(rms)
library(e1071)

library(moments)

data<-BaseMetrics
names(data)
summary(data)
data$Code_Ownership_count<-as.numeric(data$Code_Ownership_count)
data$lines_added<-as.numeric(data$lines_added)
data$lines_deleted<-as.numeric(data$lines_deleted)
str(data)
nrow(data)
drop=c("file_name","Post_release_bugs")

independant=data[,!(names(data) %in% drop)]
names(independant)

independant<-na.omit(independant)
is.na(independant)
##########correlation 
zv <- apply(independant, 2, function(independant) length(unique(independant)) == 1)
dfr <- independant[, !zv]
n=length(colnames(dfr))
correlationMatrix <- cor(dfr[,1:n],method="spearman",use="complete.obs")
correlationMatrix
# correlations <- cor(independant, method="spearman",use="pairwise.complete.obs") 
# 
# correlations
# ? findCorrelation
# is.na(correlations)
# correlations<-na.omit(correlations)

highCorr <- findCorrelation(correlationMatrix, cutoff = 0.75)
highCorr # these are column no.s
low_cor_names=names(independant[, -highCorr])
low_corr_data=independant[(names(independant) %in% low_cor_names)]
names(low_corr_data)
#metrics that have high correlation so these have to be removed
dataforredun=low_corr_data



#########start redun
#redun on everything ~ means everything nk=0 means linear combination of data
#and default r2=0.9

redun_obj = redun (~. ,data = dataforredun ,nk =0)
redun_obj
#we have to delete the redundant objects
after_redun= dataforredun[,!(names(dataforredun) %in% redun_obj $Out)]

names(after_redun)
############model

summary(data$Post_release_bugs)
#to change them into boolean  
data$Post_release_bugs>0
#string concatenation of all rows
paste(names(after_redun),collapse="+")
#to make into formula
paste("post-release-bugs>0~",paste(names(after_redun),collapse="+"))
#formula to make model
#form=as.formula(paste("'post-release-bugs'>0~",paste("pre-release-bugs","numberOfMajorBugsFoundUntil:","numberOfCriticalBugsFoundUntil:","numberOfHighPriorityBugsFoundUntil:","numberOfFixesUntil:","numberOfRefactoringsUntil:","avgLinesAddedUntil:","avgLinesRemovedUntil:","ageWithRespectTo:","weightedAgeWithRespectTo:",fanIn,fanOut,noc,numberOfAttributesInherited,numberOfMethodsInherited,numberOfPrivateAttributes,numberOfPrivateMethods,numberOfPublicAttributes,numberOfPublicMethods,CvsExpEntropy,collapse="+")))
form=as.formula(paste("Post_release_bugs>0~",paste(names(after_redun),collapse="+")))
form
#glm is generalized linear model
#model works well when it is normally distributed
?glm
#we log the data to force the data to be normally distibuted
#logistic regression
del=c("file_name")
data = data[,!(names(data) %in% del)]
#removing comp as it has charecters
model=glm(formula=form, data=log10(data+1), family = binomial(link = "logit"))
model
#null deviance show how good is the model, 1-residualdeviance/null deviance will show how good is the model.
#The higher the value the better you are

#to get p values of project
summary(model)

#the values with star are significant so we have to remove which are not significant
#new formula after removing all the insignificant values
newform=Post_release_bugs>0~lines_added+lines_deleted+Pre_release_bugs

#newmodel after removing all the insignificant values and with new formula
newmodel=glm(formula=newform, data=log10(data+1), family = binomial(link = "logit"))

summary(newmodel)

names(newmodel)
#this gives r square and we can know how good is our model
1-newmodel$deviance/newmodel$null.deviance

#we log the data in each
testdata=data.frame(lines_added=log10(mean(data$ lines_added)+1), lines_deleted=log10(mean(data$lines_deleted)+1), Pre_release_bugs=log10(mean(data$Pre_release_bugs)+1))

predict(newmodel,testdata, type="response")
#the output willgive the predicted value of post_bugs in the given project
############new increase size
#increase size to 10%
testdata=data.frame(lines_added =log10(mean(data$ lines_added)*1.1+1), lines_deleted=log10(mean(data$lines_deleted)*1.1+1), Pre_release_bugs=log10(mean(data$Pre_release_bugs)*1.1+1))


predictions<-predict(newmodel,testdata, type="response")
predictions
#the difference in the values gives the influence
?anova
anova(newmodel)



#do the same on the training and testing data sets

