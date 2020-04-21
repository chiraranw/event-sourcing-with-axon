package com.chiraranw.librarycommand.dto

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AddBkDto(var bkId: String, var title: String, var status: String)
data class BorrowBkDto(var bkId: String, var mbId: String, var status: String)
data class ReturnBkDto(var bkId: String,var mbId: String, var status: String)
data class AddMemberDto( val mbId: String, val name: String, val bksBorrowed: Int, val status: String)