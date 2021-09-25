package huji.post_pc.path2pet

data class MatchedPet(val myPetID :String, val otherPetID: String, val score: Double) {

    public fun myID() : String{
        return myPetID
    }

    public fun otherID() : String{
        return otherPetID
    }

    public fun getMatchScore() : Double{
        return score
    }


}
