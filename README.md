# Han Chinese Surnames Dataset

# Citation

Bao, H.-W.-S., Cai, H., Jing, Y., & Wang, J. (2021). Novel evidence for the increasing prevalence of unique names in China: A reply to Ogihara (2020). Frontiers in Psychology, 12, 731244. https://www.frontiersin.org/articles/10.3389/fpsyg.2021.731244/full


## Introduction
### Why this topic is interesting to me:

My motivation for exploring this topic came from the Data Is Plural — Structured Archive spreadsheet that my Computer Science teacher gave us for a project. A Han Chinese surnames dataset caught my eye. I have always been interested in names, such as their popularity, etymology, and history. I used to religiously look up names on websites such as Baby Name Wizard. However, I've always known that my name was never quite the same as my classmates. My parents gave me an American first name, but both my middle and last name are Chinese, with my middle name doubling as my Chinese name because middle names in Chinese are nonexistent and my parents wanted my Chinese name to be part of my legal name.
Chinese names reflect the wishes and expectations parents have for their children. My Chinese name/middle name, 天睿, means heavenly wisdom. My older sister's 天慧 bears a similar meaning. My parent's probably patted themselves on their backs for coming up with two names that not only rhymed but also when romanized differ only by one letter.
However, patriarchy is rooted in Chinese cultures, like most parts of the world, and affects how Chinese parents name their children. Men were often given more opportunities than women, and the expectation for them to be dominant and smart is often reflected in their names. For example, my mom's name, 小杰, means small excellence. I remember being a little shocked when she explained that her parents wanted to give her a name that was "appropriate for a woman". In other words, she was to be successful, but not too successful. That idea is not something that would typically be put on a man. This made me curious to see if positive traits were associated with boy's names, specifically higher name competence/assertiveness, name warmth/morality, and name valence ratings.
I also wondered if these positive traits correlated to a name’s popularity. In other words, would more popular names be rated higher? This would give me insight into which traits are admired more in a name, and whether popular characters are popular because of a positive connotation or not. 


### Questions

Do characters in given names that are more common in male names tend to have higher name competence/assertiveness, name warmth/morality, and name valence ratings? Will characters more common in girl’s names and/or more gender-neutral have similar, lower, or higher ratings in all these 3 categories?

Do the most popular characters in given names have higher name competence/assertiveness, name warmth/morality, and name valence ratings? How do they compare to the highest-rated characters in each category, and in which category are they the closest in rank to the highest characters? 



### Dataset Source

This dataset is from the Beijing Meiming Science and Technology Company which originally got it from the National Citizen Identity Information Center of China in 2008. According to http://www.china.org.cn/, “By collecting the identity information of China's 1.3 billion population in five years, through an investment amounting to tens of millions yuan, we have succeeded in collating the world's biggest ID database in full compliance with international standards”. This dataset was meant to protect against identity theft and fraud, in response to widescale fraud that was taking a toll on the economy. A person named 包寒吴霜 (Bruce Bao), took this data and formatted it in R and in CSV files for those who did not have the applications or ability necessary to process it in R. Extremely rare characters are not included in this dataset, and, of course, did not include any personal information. He organized the data, not according to person or identity, but by names. He states “the use of these datasets should follow the GNU GPL-3 License and the Creative Commons License CC BY-NC-SA, with a proper citation of this package and only for non-commercial purposes.” I am allowed to use it in a school setting as long as I cite it and do not profit from it. 



## Methods/Process

The process of answering my questions was a step-by-step process:

### Determining the information I would need to answer my questions
For this project, I used the givename.csv in the data-csv folder of the ChineseNames repository. To do this I would need to be able to access the actual Chinese characters of the file, of course, and the name.gender value denotes a character as feminine or masculine. I decided to use the categories of name.valence (positivity of character meaning, name.competence, and name.warmth. These are doubles that are rated by the general populace on a scale from 1-5. The differences between boys, girls, and gender-neutral characters, because these are all positive name traits that aligned with my question. 

For my second question, I had to find popular characters as well as the highest-ranked characters for each category. To find out the most popular characters, I would need the n.male and n.female (number of male and female characters who carry the name as of 2008) values for each character, added together. To find the highest-ranked characters, I would need to find the largest values in all three categories. 

For my analysis for both questions, I needed to average the values in name.valence, name.warmth, name.competence. For the first question, this would allow me to see the difference in the average values between boy, girl, and gender-neutral characters. For the second, it would allow me to see the difference in the average values between the most popular characters and the highest-ranked characters for each category. 

### Looking at code I did in class/in the past and plan how I would repurpose it for my project
I looked at the method getColumns that we coded in class and used that for my project. I also repurposed methods such as findMaxDouble and getAverage, and started my project with these simple methods that would iterate through an array list passed in. 

### Plan and code helper methods that would be used across my methods
I then needed to plan out the more complex helper methods specifically the methods to create parallel array lists of different data types, getTotalPeople, fillGenderArrays, findMostPopular,  and findHighestRanked. The parallel array list functions return values corresponding to the characters passed in (createParallelArrayListDouble), or just returned an entire column of the file as a String array list (createParallelArrayListString) corresponding to the index passed in. For getTotalPeople, I needed to add the categories of n.male and n.female together and return an array list containing Integers. fillGenderArrays populated three array lists according to the value of their name.gender value and a double passed in that would be the threshold that would determine the character’s gender. findMostPopular would use getTotalPoeple to return the characters with the highest number of people, and the number of characters included is determined by int length passed in. findHighestRanked works similarly to find most popular, except instead of using the array list from getTotalpeople it would use an array list returned by createParallelArrayListDouble and find the highest values and the number of characters included is also determined by int length passed in. 

### Put all my methods together into void methods that would provide the numerical data needed to answer my questions
Once all my helper methods were completed, I used them both within each other and in two void methods that would print out the results of my analysis. In printAverageNameValues, I created sorted the characters into three gender categories and used createParallelArrayListDouble to create three Double array lists per gender category that each held the values of name.valence, name.warmth, and name.competence. I then got the average of each of these double arrays and printed out the average for these three traits for each gender category. I did something similar for compareCommonAverageCharacterRatings, except instead of gender categories I used the return of findMostPopular and findHighestRanked.

### Ran them in my main method

I made a new file givenName with the path name to the givenname.csv file on my laptop. I declared and initialized an array numTraits with the strings name.valence, name.warmth, and name.competence. I then called my void methods, passed in the givenname.csv file, and for compareCommonAverageCharacterRatings I decided randomly on a length of ten. 

## Challenges

Every coding project tests my patience, and this was no different. There were a couple of complications that ate up time, the main one being that the indexOf method did not work as intended when I used it to try to find the index of a string in the array list that held my column names. When I expected a 0 to be returned, I instead got a -1, which resulted in many indexOutOfBounds errors in my program. I eventually had to hard code this, otherwise, the methods would not work. I know now this problem comes when data is generated on a MacBook, and now that I know this problem I look forward to hopefully recoding this part! I also ran into several NumberFormatException runtime errors, which I decided to throw instead of try catch. This error arises when parsing Strings as Integers or Doubles, and I got around this by making sure the values I was passing in were able to be parsed as Integers and Doubles. I ran into many IndexOutOfBounds exceptions, and this mostly resulted from me rushing my code and not planning it out as well as I could have. In the future, I will be sure to hopefully go more slowly and catch these errors before using these methods in my code. In the end, I just came down to being more careful and making sure the index I was trying to access existed in the array list. 


## Results and conclusion

First Question: My data showed that the values I was analyzing between girl, boy, and gender-neutral characters were quite similar to each other, with girl’s names ranking higher than boy’s names in name.valence, and name.warmth. However, in name.competence the average value for boy characters was higher, which might represent an interesting cultural aspect of naming. Of course, this one number does not speak for an entire naming trend, but I believe that it is a starting point for further investigation. I found it interesting that gender-neutral characters never had the highest average for any of the three categories, instead being in the middle for name competence and the lowest for the other two. Of course, these numbers cannot explain an entire naming trend, but I would like to further investigate if this represents a correlation that more gender-neutral names have lower ratings. Overall, name.competence was higher in boys' names, which tells me that is a valued trait in a boy's name, whereas in girls' names name.valence and name.warmth are prioritized more. This is different from what I thought it would be, as I expected boy’s names to be the highest for all three categories. 

Second Question: Popular characters did not often have the highest ratings, with the average ratings of the x most popular characters compared to the average of the x highest rated characters being a pretty significant difference. Of course, these ratings do not explain an entire naming trend, but I am wondering why popular names are lower rated. I hypothesize that people rated these characters lower because they are just so common, and they appreciate more unique characters as opposed to more mainstream ones. This contradicted my original hypothesis, in which I thought popular names were popular because people thought they were positive and rated them accordingly. This brings up an interesting idea that people might name their kids with popular characters for conformity, not for positive traits. 

## Additional Exploration

If I had more time, I would have tried to scan the province names file and calculate the percentage of people in each province with the most popular characters in their given names. However, being short on time and having the indexOf method not working the way I was expecting, this proved difficult, especially when making the parallel arrays such as province names and their corresponding populations. The problem was primarily with accessing the first column of the file, and with the first column being the characters, this proved to be an issue that I ended up not having time to work around. Adding to this difficulty, the file formatting was also something I had to work around, as I would be reading data from two files at a time that is organized differently. However, once I get the indexOf conflict resolved I would love to give this a shot and answer an original question I had about naming trends and their correlation with population density within provinces. 

It would also be interesting to see a data set of name changes among the Chinese populace. I read an article describing the pressure for people with rarer Chinese names to switch to a more common one. Rare characters aren’t often recognized to hinder everyday things such as applying for a passport or job applications, and extremely rare characters are not included in this dataset. I would like to see what percentage of people with rare characters in their names ended up changing them, and how popular the character they changed their name to was. 

