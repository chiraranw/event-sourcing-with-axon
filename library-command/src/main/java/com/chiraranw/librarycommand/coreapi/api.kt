package com.chiraranw.librarycommand.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

/** --------- Book API ------ **/
//commands
data class AddBkCommand(@TargetAggregateIdentifier var bkId: String, var title: String, var status: String)
data class BorrowBkCommand(@TargetAggregateIdentifier var bkId: String, var mbId: String,var status: String)
data class ReturnBkCommand(@TargetAggregateIdentifier var bkId: String, var mbId: String, var status: String)

data class RemoveBkCommand(@TargetAggregateIdentifier var bkId: String, var status: String)

//events
data class BkAddedEvent(val bkId: String, val title: String, val status: String)
data class BkBorrowedEvent(val bkId: String, val mbId: String, val status: String)
data class BkReturnedEvent(val bkId: String, val mbId: String,val status: String)
data class BkRemovedEvent(val bkId: String, val status: String)

/** ------- Member API ------- **/
//commands
data class AddMemberCommand(@TargetAggregateIdentifier var mbId: String, var name: String, var bksBorrowed: Int, var status: String)
data class RemoveMemberCommand(@TargetAggregateIdentifier var mbId: String, var status: String)

//events
data class MemberAddedEvent(val mbId: String, val name: String, val bksBorrowed: Int, val status: String)
data class MemberRemovedEvent(val mbId: String, val status: String)