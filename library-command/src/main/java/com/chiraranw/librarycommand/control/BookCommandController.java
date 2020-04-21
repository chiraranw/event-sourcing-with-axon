package com.chiraranw.librarycommand.control;

import com.chiraranw.librarycommand.coreapi.AddBkCommand;
import com.chiraranw.librarycommand.coreapi.BorrowBkCommand;
import com.chiraranw.librarycommand.coreapi.RemoveBkCommand;
import com.chiraranw.librarycommand.dto.AddBkDto;
import com.chiraranw.librarycommand.dto.BorrowBkDto;
import com.chiraranw.librarycommand.dto.ReturnBkDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CompletableFuture<String> createBook(@RequestBody AddBkDto addBkDto) {
        String id = UUID.randomUUID().toString();
        return this.commandGateway
                .send(new AddBkCommand(id, addBkDto.getTitle(), addBkDto.getStatus()))
                .thenApply(res -> "Book added: " + id)
                .exceptionally(Throwable::getLocalizedMessage);
    }

    @RequestMapping(value = "/remove/{bkId}", method = RequestMethod.DELETE)
    public CompletableFuture<String> removeBook(@PathVariable("bkId") String bkId) {
        return this.commandGateway
                .send(new RemoveBkCommand(bkId, "unavailable"))
                .thenApply(res -> "Book removed: " + bkId)
                .exceptionally(Throwable::getLocalizedMessage);
    }

    @RequestMapping(value = "/borrow", method = RequestMethod.POST)
    public CompletableFuture<String> borrowBook(@RequestBody BorrowBkDto borrowBkDto) {
        return this.commandGateway
                .send(new BorrowBkCommand(borrowBkDto.getBkId(), borrowBkDto.getMbId(), borrowBkDto.getStatus()))
                .thenApply(res -> "Book borrowed: " + borrowBkDto.getBkId())
                .exceptionally(Throwable::getLocalizedMessage);
    }

    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public CompletableFuture<String> returnBook(@RequestBody ReturnBkDto returnBkDto) {
        return this.commandGateway
                .send(new BorrowBkCommand(returnBkDto.getBkId(), returnBkDto.getMbId(), returnBkDto.getStatus()))
                .thenApply(res -> "Book returned: " + returnBkDto.getBkId())
                .exceptionally(Throwable::getLocalizedMessage);
    }


}
