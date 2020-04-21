package com.chiraranw.librarycommand.aggregate;

import com.chiraranw.librarycommand.coreapi.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class BookAggregate {

    @AggregateIdentifier
    private String bkId;
    private String title;
    private String status;


    //New Book
    @CommandHandler
    public BookAggregate(AddBkCommand command) {
        //Validate
        System.out.println("Applying the command {}"+command);
        AggregateLifecycle.apply(new BkAddedEvent(command.getBkId(), command.getTitle(), command.getStatus()));
    }

    @EventSourcingHandler
    public void on(BkAddedEvent event) {
        System.out.println("Sourcing the event the command {}"+event);
        this.bkId = event.getBkId();
        this.title = event.getTitle();
        this.status = event.getStatus();
    }

    //Borrowing
    @CommandHandler
    public void handle(BorrowBkCommand command) {
        System.out.println("Applying the command {}"+command);
        //Validate
        AggregateLifecycle.apply(new BkBorrowedEvent(
                command.getBkId(),
                command.getMbId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(BkBorrowedEvent event) {
        System.out.println("Sourcing the event the command {}"+event);
        this.bkId = event.getBkId();
        this.status = event.getStatus();
    }

    //Returning
    @CommandHandler
    public void handle(ReturnBkCommand command) {
        System.out.println("Applying the command {}"+command);
        //Validate
        AggregateLifecycle.apply(new BkReturnedEvent(
                command.getBkId(),
                command.getMbId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(BkReturnedEvent event) {
        System.out.println("Sourcing the event the command {}"+event);
        this.bkId = event.getBkId();
        this.status = event.getStatus();
    }

    //Removing book
    @CommandHandler
    public void handle(RemoveBkCommand command) {
        System.out.println("Applying the command {}"+command);
        //Validate
        AggregateLifecycle.apply(new BkRemovedEvent(
                command.getBkId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(BkRemovedEvent event) {
        System.out.println("Sourcing the event the command {}"+event);
        this.bkId = event.getBkId();
        this.status = event.getStatus();
    }


}
