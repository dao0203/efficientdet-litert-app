package com.example.efficientdet_litert_app.domain

import java.nio.FloatBuffer

enum class Category(val id: Int, val label: String) {
    PERSON(1, "person"),
    BICYCLE(2, "bicycle"),
    CAR(3, "car"),
    MOTORCYCLE(4, "motorcycle"),
    AIRPLANE(5, "airplane"),
    BUS(6, "bus"),
    TRAIN(7, "train"),
    TRUCK(8, "truck"),
    BOAT(9, "boat"),
    TRAFFIC_LIGHT(10, "traffic light"),
    FIRE_HYDRANT(11, "fire hydrant"),
    STOP_SIGN(13, "stop sign"),
    PARKING_METER(14, "parking meter"),
    BENCH(15, "bench"),
    BIRD(16, "bird"),
    CAT(17, "cat"),
    DOG(18, "dog"),
    HORSE(19, "horse"),
    SHEEP(20, "sheep"),
    COW(21, "cow"),
    ELEPHANT(22, "elephant"),
    BEAR(23, "bear"),
    ZEBRA(24, "zebra"),
    GIRAFFE(25, "giraffe"),
    BACKPACK(27, "backpack"),
    UMBRELLA(28, "umbrella"),
    HANDBAG(31, "handbag"),
    TIE(32, "tie"),
    SUITCASE(33, "suitcase"),
    FRISBEE(34, "frisbee"),
    SKIS(35, "skis"),
    SNOWBOARD(36, "snowboard"),
    SPORTS_BALL(37, "sports ball"),
    KITE(38, "kite"),
    BASEBALL_BAT(39, "baseball bat"),
    BASEBALL_GLOVE(40, "baseball glove"),
    SKATEBOARD(41, "skateboard"),
    SURFBOARD(42, "surfboard"),
    TENNIS_RACKET(43, "tennis racket"),
    BOTTLE(44, "bottle"),
    WINE_GLASS(46, "wine glass"),
    CUP(47, "cup"),
    FORK(48, "fork"),
    KNIFE(49, "knife"),
    SPOON(50, "spoon"),
    BOWL(51, "bowl"),
    BANANA(52, "banana"),
    APPLE(53, "apple"),
    SANDWICH(54, "sandwich"),
    ORANGE(55, "orange"),
    BROCCOLI(56, "broccoli"),
    CARROT(57, "carrot"),
    HOT_DOG(58, "hot dog"),
    PIZZA(59, "pizza"),
    DONUT(60, "donut"),
    CAKE(61, "cake"),
    CHAIR(62, "chair"),
    COUCH(63, "couch"),
    POTTED_PLANT(64, "potted plant"),
    BED(65, "bed"),
    DINING_TABLE(67, "dining table"),
    TOILET(70, "toilet"),
    TV(72, "tv"),
    LAPTOP(73, "laptop"),
    MOUSE(74, "mouse"),
    REMOTE(75, "remote"),
    KEYBOARD(76, "keyboard"),
    CELL_PHONE(77, "cell phone"),
    MICROWAVE(78, "microwave"),
    OVEN(79, "oven"),
    TOASTER(80, "toaster"),
    SINK(81, "sink"),
    REFRIGERATOR(82, "refrigerator"),
    BOOK(84, "book"),
    CLOCK(85, "clock"),
    VASE(86, "vase"),
    SCISSORS(87, "scissors"),
    TEDDY_BEAR(88, "teddy bear"),
    HAIR_DRIER(89, "hair drier"),
    TOOTHBRUSH(90, "toothbrush");

    companion object {
        private val map = entries.associateBy(Category::id)

        fun fromId(id: Int): Category? {
            return map[id + 1]
        }
    }
}

fun FloatBuffer.toCategories(): List<Category?> {
    val categories = mutableListOf<Category?>()
    for (i in 0 until limit()) {
        categories.add(Category.fromId(get(i).toInt()))
    }
    return categories
}