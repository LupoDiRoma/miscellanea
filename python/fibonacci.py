# a computationally efficient implementation of recursive Fibonacci with
# an in memory cache for already computed values.


def fibonacci(n):
    def fib(n, cache={0:0,1:1}):
        if n<=0 or n==1:
            return cache.get(n)
        else:
            value = cache.get(n)
            if value == None:
                value = fib(n-1,cache) + fib(n-2,cache)
                cache.update({n:value})
            return value
    return fib(n)
