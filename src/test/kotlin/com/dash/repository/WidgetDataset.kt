package com.dash.repository

import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)

@SqlGroup(
    Sql(
        statements = [
            "INSERT INTO tab (id, label, tab_order) VALUES ('11', 'Météo', 1);" +
            "INSERT INTO widget (id, type, data, widget_order, tab_id) VALUES (121, 1, '{\"city\": \"Paris\"}', 1, 11);"
        ],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    ),
    Sql(statements = ["DELETE FROM widget; DELETE FROM tab;"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)

annotation class WidgetDataset
