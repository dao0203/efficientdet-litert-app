package com.example.gemma2_litert_app.domain

data class DetectionResult(
    val locations: List<BoundingBox>,
    val category: FloatArray,
    val score: FloatArray,
    val numberOfDetection: FloatArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetectionResult

        if (locations != other.locations) return false
        if (!category.contentEquals(other.category)) return false
        if (!score.contentEquals(other.score)) return false
        if (!numberOfDetection.contentEquals(other.numberOfDetection)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = locations.hashCode()
        result = 31 * result + category.contentHashCode()
        result = 31 * result + score.contentHashCode()
        result = 31 * result + numberOfDetection.contentHashCode()
        return result
    }
}
