

class OptionalEnrollment:

    def __init__(self, username, cid):
        self.__username = username
        self.__cid = cid

    @property
    def username(self):
        return self.__username

    @property
    def cid(self):
        return self.__cid

    def __str__(self):
        return "'" + self.__username + "', " + str(self.__cid)
