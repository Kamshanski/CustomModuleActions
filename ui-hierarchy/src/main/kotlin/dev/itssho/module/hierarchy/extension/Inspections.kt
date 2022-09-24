package dev.itssho.module.hierarchy.extension

import dev.itssho.module.hierarchy.HierarchyObject
// TODO добавить проверку, что объект HierarchyObject уникальный в дереве.
//  Если создать ноду вне дерева, и вставить её в две родительские ноды, то родителем ноды станет последняя.
//  А при доставании ноды из через children из первого родителя даст неверный id, потому что родитель не верный.
//  Крч, надо написать проверку со сравниванием по ссылке всех объектов в дереве.
//  UPD:  checkHierarchyIdsUniqueness выплюнет ошибку в таком случае. Т.к. нода встретится 2 раза. Т.е. можно не писать

// TODO Вытащить это и ещё AttributesViolationException в утилитный модуль ui-hierarchy-util
fun checkHierarchyIdsUniqueness(node: HierarchyObject, existingIds: MutableSet<String> = HashSet()) {
	if (node.id in existingIds) {
		throw IllegalArgumentException("Id '${node.id}' is noе unique")
	}
	existingIds += node.id

	for (child in node.children) {
		checkHierarchyIdsUniqueness(child, existingIds)
	}
}

fun checkPersonalIdsChars(node: HierarchyObject) {
	node.forEach {
		if (it.personalId.any { char -> !char.isLetterOrDigit() }) {
			throw IllegalArgumentException("Id '${it.personalId}' contains invalid chars. PersonalId must contain only letters or digits.")
		}
	}
}

fun checkAttributesUniqueness(node: HierarchyObject) {
	if (node.attrs.distinctBy { it.attrId }.size != node.attrs.size) {
		throw IllegalArgumentException("Attributes in node '${node.id}' are not unique")
	}

	for (child in node.children) {
		checkAttributesUniqueness(child)
	}
}

fun checkActionsUniqueness(node: HierarchyObject) {
	if (node.actions.distinct().size != node.actions.size) {
		throw IllegalArgumentException("Actions in node '${node.id}' are not unique")
	}

	for (child in node.children) {
		checkActionsUniqueness(child)
	}
}