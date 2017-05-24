# SoloAlgorithmsProject4
This Project demonstrates my knowledge and mastery of my ability to understand complex Linear Programming problems

Honors Algorithms Course - Hotels Problem   
Students are not allowed to share their work WITH EACH OTHER. All work is to be done alone.  
California State University - Long Beach  
This is a Simplex problem solved by pushing flow throughout the Graph and making forward edges have a  
limit of infinity.  Finding the Min-Cut of the problem allows us to maximize the profit gained from  
the investors.  

You are a large hotel magnate and are looking to build a hotel in New York. In  
order to do so, you will need to attract potential investors. There are n possible  
amenities that you can include in your hotel: maid service, room service, goldplated  
shower handles, and so on. Each of these amenities comes at a cost, cj  
for the jth amenity where j ∈ {0, 1, 2, . . . , n − 1}.  
There are m potential investors, and investor i is willing to give you a total  
of pi where i ∈ {0, 1, 2, . . . , m − 1}... but there’s a catch. Investor i is only  
willing to invest if you include a set of amenities Si ⊆ {0, 1, 2, . . . , n − 1} in  
your hotel. Each investor has a different set of requirements. A given investor  
doesn’t care if there are extra amenities, only that the amenities in the set Si  
are included.  
As the hotel magnate, you get to keep all the money leftover after the hotel  
is built. So your goal will be to maximize the money leftover from the investors’  
money after paying for all of their required amenities. Note that once you have  
paid for a specific amenity, you will not have to pay for it again. In other words,  
if both investors 1 and 2 demand maid service, you only need to pay for it once.  
You will write a program that outputs the numbers of the investors that you  
plan to take money from.  
Consider the following information from the file information.txt.  
29602 24772 77838 790 72635  
25076 12538 27586 25076 25077 25077 27586 12537 25077  
0 2 3 4  
0 2 3  
0 1 3  
0 1 2 3 4  
0 1 2 3 4  
0 1 2 3 4  
0 1 3  
1 2 3  
0 1 2 3 4  
In this case, there are 5 potential amenities (the first line, numbered from 0 to  
4 from left to right) and 9 potential investors (the second line, numbered from  
0 to 8 from left to right). The first investor is demanding that amenities 0, 2,  
3, and 4 all be provided. The second investor is demanding only 0, 2, and 3.  
And so on. You should only take money from investors 2 and 6 (and therefore  
puchase amenities 0, 1, and 3). The output file investors.txt should therefore  
be  
2  
6  
