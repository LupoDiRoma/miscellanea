# a queue based on a two stack implementation. One stack (first) is used for
# push operations. The other (second) is refilled whenever pop is called on the
# queue and the second stack is empty. This is filled with all the elements
# from the first stack, then the first stack is cleared before returning
# the head of the second stack (pop operation)
# Note that we are filling the second stack reading iteratively from the first
# one and in doing so we reverse the sequence, thus the end of the queue is
# always at the top of the second stack.

class Queue2Stacks(object):
    def __init__(self):
        self.first = []
        self.second = []

    def _refill(self):
        if len(self.first)>0:
            for e in self.first:
                self.second.append(e)
            self.first = []

    def peek(self):
        if len(self.second)==0:
            self._refill()
        if len(self.second)>0:
            return self.second[0]
        else:
            return None

    def pop(self):
        if len(self.second)>0:
            e = self.second[0]
            self.second = self.second[1:]
            return e
        else:
            if len(self.first)>0:
                self._refill()
                return self.pop()
            else:
                return None

    def put(self, value):
        self.first.append(value)
