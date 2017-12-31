package uk.co.sentinelweb.domain


data class ColourDomain constructor(val r:Float, val g:Float, val b:Float, val a:Float) {
    companion object {
        val WHITE = ColourDomain(1f,1f,1f,1f)
        val RED = ColourDomain(1f,0f,0f,1f)
        val GREEN = ColourDomain(1f,1f,0f,1f)
        val BLUE = ColourDomain(0f,0f,1f,1f)
        val BLACK = ColourDomain(0f,0f,0f,1f)
        val TRANSPARENT = ColourDomain(0f,0f,0f,0f)

        fun toInteger(c: ColourDomain) : Int {
            return (c.a * 255).toInt() and 0xFF shl 24 or
                    ((c.r * 255).toInt() and 0xFF shl 16) or
                    ((c.g * 255).toInt() and 0xFF shl 8) or
                    ((c.b * 255).toInt() and 0xFF shl 0)
        }

        fun fromInteger(input:Int): ColourDomain {
            return ColourDomain(
                    (input shr 16 and 0xFF)/255f,
                    (input shr 8 and 0xFF)/255f,
                    (input shr 0 and 0xFF)/255f,
                    (input shr 24 and 0xFF)/255f
            )
        }

        fun fromInts(r: Int, g: Int, b: Int, a: Int): ColourDomain {
            return ColourDomain(
                    (r and 0xFF) / 255f,
                    (g and 0xFF) / 255f,
                    (b and 0xFF) / 255f,
                    (a and 0xFF) / 255f
            )
        }
    }
}
