# Given a sentence with multiple blank spaces between words and an optional
# period following the last word, reverse the sentence word by word, append the
# period to the new last word and only have one space between words in the final
# sentence

input = '    this is a  string that  we need     to  parse. '
print input

# the proposed solution:
# remove trailing spaces
# remove remove any sequence of blan longer than 2
# split the sences into a word array and reverse it
# strip the period if present and set a flag
# write the reversed word array, adding spaces as needed
# append a period if required

hasPeriod = False
input = input.strip()
input.replace('\[ ]{2,}','')
words = input.split(' ')
last_word = words[len(words)-1]
if last_word.index('.')==len(last_word)-1:
    hasPeriod = True
    words[len(words)-1]=last_word[0:len(last_word)-1]
words.reverse
result = ''
for word in words:
    if word!='':
        if len(result)==0:
            result =   word
        else:
            result = word+' '+result
if hasPeriod:
    result = result + '.'
print result
