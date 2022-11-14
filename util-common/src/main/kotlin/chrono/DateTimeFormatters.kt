package chrono

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

val SIMPLE_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
	.appendValue(ChronoField.HOUR_OF_DAY, 2)
	.appendLiteral(':')
	.appendValue(ChronoField.MINUTE_OF_HOUR, 2)
	.optionalStart()
	.appendLiteral(':')
	.appendValue(ChronoField.SECOND_OF_MINUTE, 2)
	.optionalStart()
	.appendFraction(ChronoField.MILLI_OF_SECOND, 3, 3, true)
	.toFormatter(Locale.GERMANY)