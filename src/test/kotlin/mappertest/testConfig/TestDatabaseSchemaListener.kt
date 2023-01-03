package mappertest.testConfig

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan
import mappertest.testConfig.DataSourceConfiguration.Companion.ORIGINAL_DATABASE_NAME
import mappertest.testConfig.DataSourceConfiguration.Companion.TEST_DATABASE_NAME
import org.springframework.jdbc.core.JdbcTemplate

@AutoScan
class TestDatabaseSchemaListener(
    private val jdbcTemplate: JdbcTemplate
) : ProjectListener {
    override suspend fun beforeProject() {
        jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS $TEST_DATABASE_NAME")
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS $TEST_DATABASE_NAME.user LIKE $ORIGINAL_DATABASE_NAME.user")
    }

    override suspend fun afterProject() {
        jdbcTemplate.execute("DROP DATABASE IF EXISTS $TEST_DATABASE_NAME")
    }
}
