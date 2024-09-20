# 斐波那数列的生成器
def fib():
    a, b = 0, 1
    while 1:
        yield a
        a, b = b, a + b


# b = fib()
# for i in range(10):
#     print b.next()

