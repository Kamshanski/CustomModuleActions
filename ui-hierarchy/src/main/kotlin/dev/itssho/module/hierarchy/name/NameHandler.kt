package dev.itssho.module.hierarchy.name

interface NameHandler {

	/** Initial module name value */
	fun getInitialName(): String {
		return ""
	}

	/**
	 * Do not implement default validation rules:
	 * 	ERROR - Non Ascii letters
	 * 	ERROR - Illegal chars: < > : " / \ | ? * \n \t \b \r <Space>
	 * 	ERROR - Empty fullName
	 * 	WARNING - Starts with non-letter
	 */
	fun validate(fullName: String, reporter: IssueReporter)
}