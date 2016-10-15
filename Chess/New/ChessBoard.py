import time,copy,random
import Pieces

class Board(object):
    def __init__(self):
        self.remainingWhitePieces = []
        self.remainingBlackPieces = []
        self.history = []
        self.layout = {}
        self.letters = "ABCDEFGH"
        self.numbers = "12345678"
    
        for i in xrange(8):
            #Black Pawns
            newPawn = Pieces.Pawn(self.letters[i]+"7", "Black", self)
            self.remainingBlackPieces.append(newPawn)
            self.layout[newPawn.getPosition()] = newPawn
        
        for i in xrange(8):
            #White Pawns
            newPawn = Pieces.Pawn(self.letters[i]+"2", "White", self)
            self.remainingWhitePieces.append(newPawn)
            self.layout[newPawn.getPosition()] = newPawn
            
        #Rooks
        newRook = Pieces.Rook("A8","Black", self)
        self.remainingBlackPieces.append(newRook)
        self.layout[newRook.getPosition()] = newRook
        
        newRook = Pieces.Rook("H8","Black", self)
        self.remainingBlackPieces.append(newRook)
        self.layout[newRook.getPosition()] = newRook
        
        newRook = Pieces.Rook("A1","White", self)
        self.remainingWhitePieces.append(newRook)
        self.layout[newRook.getPosition()] = newRook
        
        newRook = Pieces.Rook("H1","White", self)
        self.remainingWhitePieces.append(newRook)
        self.layout[newRook.getPosition()] = newRook
        
        
        #Knights
        newKnight = Pieces.Knight("B8","Black", self)
        self.remainingBlackPieces.append(newKnight)
        self.layout[newKnight.getPosition()] = newKnight
        
        newKnight = Pieces.Knight("G8","Black", self)
        self.remainingBlackPieces.append(newKnight)
        self.layout[newKnight.getPosition()] = newKnight


        newKnight = Pieces.Knight("B1","White", self)
        self.remainingWhitePieces.append(newKnight)
        self.layout[newKnight.getPosition()] = newKnight


        newKnight = Pieces.Knight("G1","White", self)
        self.remainingWhitePieces.append(newKnight)
        self.layout[newKnight.getPosition()] = newKnight
        
        #Bishops
        newBishop = Pieces.Bishop("C8","Black", self)
        self.remainingBlackPieces.append(newBishop)
        self.layout[newBishop.getPosition()] = newBishop
        
        newBishop = Pieces.Bishop("F8","Black", self)
        self.remainingBlackPieces.append(newBishop)
        self.layout[newBishop.getPosition()] = newBishop
        
        newBishop = Pieces.Bishop("C1","White", self)
        self.remainingWhitePieces.append(newBishop)
        self.layout[newBishop.getPosition()] = newBishop
        
        newBishop = Pieces.Bishop("F1","White", self)
        self.remainingWhitePieces.append(newBishop)
        self.layout[newBishop.getPosition()] = newBishop
        
        #Queens
        newQueen = Pieces.Queen("D8","Black", self)
        self.remainingBlackPieces.append(newQueen)
        self.layout[newQueen.getPosition()] = newQueen
        
        newQueen = Pieces.Queen("D1","White", self)
        self.remainingWhitePieces.append(newQueen)
        self.layout[newQueen.getPosition()] = newQueen
        
        #Kings
        newKing = Pieces.King("E8","Black", self)
        self.remainingBlackPieces.append(newKing)
        self.layout[newKing.getPosition()] = newKing
        
        newKing = Pieces.King("E1","White", self)
        self.remainingWhitePieces.append(newKing)
        self.layout[newKing.getPosition()] = newKing
        
        for letter in self.letters:
            for number in self.numbers:
                if letter+number not in self.layout.keys():
                    self.layout[letter+number] = 0

    
    def __eq__(self, val):
        pass
    
    def __ne__(self,val):
        return not(self.__eq__(val))
        
    def loadBoard(self, gameDoc):
        pass
        
    def __str__(self):
        return str(self.layout)
    
    def resetBoard(self):
        self.__init__()

    def getPieceAt(self, position):
        position = position.upper()
        assert len(position) == 2
        assert (position[0] in self.letters) and (position[1] in self.numbers)
        return self.layout[position]

    def getWhitePieces(self):
        return self.remainingWhitePieces
    def getBlackPieces(self):
        return self.remainingBlackPieces
    
    def getAllRemainingPieces(self):
        return self.remainingWhitePieces + self.remainingBlackPieces
        
    def saveGame(self):
        pass