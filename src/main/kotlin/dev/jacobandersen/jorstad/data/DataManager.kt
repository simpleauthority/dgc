package dev.jacobandersen.jorstad.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.jacobandersen.jorstad.data.textcommands.TextCommandDam
import dev.jacobandersen.jorstad.util.Log
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

class DataManager {
    private val hikari: HikariDataSource
    val jdbi: Jdbi
    val textCommand: TextCommandDam

    init {
        Log.info("Configuring data storage system...")
        val config = HikariConfig()
        config.poolName = "JorstadBot SQLite Pool"
        config.jdbcUrl = "jdbc:sqlite:data/jorstad.db"

        Log.info("Connecting to data storage...")
        hikari = HikariDataSource(config)
        jdbi = Jdbi.create(hikari)
        jdbi.installPlugin(SqlObjectPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())

        Log.info("Creating data accessors...")
        textCommand = TextCommandDam(this)

        Log.info("Creating data containers...")
        textCommand.createContainer()
    }
}