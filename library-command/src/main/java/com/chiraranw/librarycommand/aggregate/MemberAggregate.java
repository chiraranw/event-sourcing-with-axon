package com.chiraranw.librarycommand.aggregate;

import com.chiraranw.librarycommand.coreapi.AddMemberCommand;
import com.chiraranw.librarycommand.coreapi.MemberAddedEvent;
import com.chiraranw.librarycommand.coreapi.MemberRemovedEvent;
import com.chiraranw.librarycommand.coreapi.RemoveMemberCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class MemberAggregate {

    @AggregateIdentifier
    private String mbId;
    private String name;
    private String status;
    private int bksBorrowed;

    @CommandHandler
    public MemberAggregate(AddMemberCommand command) {
        //To DO - add validation here! No members with same id
        System.out.println("Creating member......");
        AggregateLifecycle.apply(
                new MemberAddedEvent(command.getMbId(), command.getName(), command.getBksBorrowed(), command.getStatus())
        );
    }

    @EventSourcingHandler
    public void on(MemberAddedEvent event) {
        this.mbId = event.getMbId();
        this.name = event.getName();
        this.bksBorrowed = event.getBksBorrowed();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(RemoveMemberCommand command) {
        //Validation here!
        AggregateLifecycle.apply(
                new MemberRemovedEvent(command.getMbId(), command.getStatus())
        );
    }

    @EventSourcingHandler
    public void on(MemberRemovedEvent event) {
        //this.mbId = event.getMbId();
        this.status = event.getStatus();
    }
}
