

class Student:

    def __init__(self, name, username, group1, group2=None):
        self.__name = name
        self.__username = username
        self.__group1 = group1
        self.__group2 = group2

    @property
    def username(self):
        return self.__username

    @property
    def name(self):
        return self.__name

    @property
    def group1(self):
        return self.__group1

    @property
    def group2(self):
        return self.__group2

    def __str__(self):
        if self.__group2 == None:
            return "'" + self.__name + "', '" + self.__username + "', " + str(self.__group1)
        return "'" + self.__name + "', '" + self.__username + "', " + str(self.__group1) + ", " + str(self.__group2)
