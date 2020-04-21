package com.chiraranw.librarycommand.control;

import com.chiraranw.librarycommand.coreapi.AddMemberCommand;
import com.chiraranw.librarycommand.coreapi.RemoveMemberCommand;
import com.chiraranw.librarycommand.dto.AddMemberDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/members")
public class MemberCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public MemberCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CompletableFuture<String> create(@RequestBody AddMemberDto dto) {
        String mbId = UUID.randomUUID().toString();
        return this.commandGateway
                .send(new AddMemberCommand(mbId, dto.getName(), dto.getBksBorrowed(), dto.getStatus()))
                .thenApply(res -> "Member added: " + mbId)
                .exceptionally(Throwable::getLocalizedMessage);
    }


    @RequestMapping(value = "/remove/{mbId}", method = RequestMethod.POST)
    public CompletableFuture<String> removeMember(@PathVariable("mbId") String mbId) {
        System.out.println("Here?.....................");
        return this.commandGateway
                .send(new RemoveMemberCommand(mbId, "disabled"))
                .thenApply(res -> "Member removed :" + mbId)
                .exceptionally(Throwable::getLocalizedMessage);
    }


}
