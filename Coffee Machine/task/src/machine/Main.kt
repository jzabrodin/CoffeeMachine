package machine

import kotlin.system.exitProcess

enum class CoffeeType(
    val code: Int,
    val waterPortion: Int,
    val coffeePortion: Int,
    val milkPortion: Int,
    val price: Int
) {
    ESPRESSO(1, 250, 16, 0, 4),
    LATTE(2, 350, 20, 75, 7),
    CAPPUCCINO(3, 200, 12, 100, 6),
}

class CoffeeMachine {
    private var totalMoney = 550
    private var totalWater = 400
    private var totalMilk = 540
    private var totalCoffee = 120
    private var totalCups = 9

    fun buyCoffee(choice: String): Unit {
        val coffee: CoffeeType = when (choice) {
            "1" -> {
                CoffeeType.ESPRESSO
            }
            "2" -> {
                CoffeeType.LATTE
            }
            "3" -> {
                CoffeeType.CAPPUCCINO
            }
            "back" -> return
            else -> throw Exception("Invalid type")
        }

        val cups = 1

        val availableCups = getAvailablePortions(coffee)

        var success = false

        if (availableCups > cups) {
            val delta = availableCups - cups
            println("Yes, I can make that amount of coffee (and even $delta more than that)")
            success = true
        }

        if (availableCups == cups) {
            println("Yes, I can make that amount of coffee ")
            success = true
        }

        if (availableCups < cups) {
            println("No, I can make only $availableCups cups of coffee")
        }

        if (success) {
            totalCoffee -= coffee.coffeePortion
            totalMilk -= coffee.milkPortion
            totalWater -= coffee.waterPortion
            totalCups -= cups
            totalMoney += coffee.price
        }
    }

    private fun getAvailablePortions(coffe: CoffeeType): Int {
        var availableCups = 0
        val coffeeForCups = totalCoffee / coffe.coffeePortion
        if (coffe.milkPortion != 0) {
            val milkForCups = totalMilk / coffe.milkPortion
            availableCups = if (coffeeForCups > milkForCups) milkForCups else coffeeForCups

            val waterForCups = totalWater / coffe.waterPortion
            availableCups = if (waterForCups > availableCups) availableCups else waterForCups
        } else {
            val waterForCups = totalWater / coffe.waterPortion
            availableCups = if (coffeeForCups > waterForCups) coffeeForCups else waterForCups
        }

        return if (availableCups >= totalCups) totalCups else availableCups
    }


    fun showStatus() {
        println("The coffee machine has:")
        println("$totalWater ml of water")
        println("$totalMilk ml of milk")
        println("$totalCoffee g of coffee beans")
        println("$totalCups disposable cups")
        println("\$$totalMoney of money")
        println()
    }

    private fun fillCoffee() {
        println("Write how many ml of water do you want to add:")
        val water = readln().toInt()
        println("Write how many ml of milk do you want to add:")
        val milk = readln().toInt()
        println("Write how many grams of coffee beans do you want to add:")
        val coffee = readln().toInt()
        println("Write how many disposable cups of coffee do you want to add:")
        val cups = readln().toInt()

        totalCups += cups
        totalCoffee += coffee
        totalWater += water
        totalMilk += milk
    }

    fun takeCoffee() {
        println("I gave you \$$totalMoney")
        println()
        totalMoney = 0
    }

    fun processCommand(command: String) {
        when (command) {
            "buy" -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                buyCoffee(readln())
            }
            "fill" -> fillCoffee()
            "take" -> takeCoffee()
            "remaining" -> showStatus()
            "exit" -> exitProcess(0)
            else -> throw Exception("Invalid action")
        }
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine()

    while (true) {
        println("Write action (buy, fill, take, remaining, exit):")
        val command = readln()
        coffeeMachine.processCommand(command)
    }
}
// 300-65-111-1
// 599-250-200-10-no
