package com.example.efficientdet_litert_app.domain

data class DetectionResult(
    val locations: List<BoundingBox>,
    val categories: List<Category?>,
    val score: FloatArray,
    val numberOfDetection: FloatArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetectionResult

        if (locations != other.locations) return false
        if (categories != other.categories) return false
        if (!score.contentEquals(other.score)) return false
        if (!numberOfDetection.contentEquals(other.numberOfDetection)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = locations.hashCode()
        result = 31 * result + categories.hashCode()
        result = 31 * result + score.contentHashCode()
        result = 31 * result + numberOfDetection.contentHashCode()
        return result
    }
}
