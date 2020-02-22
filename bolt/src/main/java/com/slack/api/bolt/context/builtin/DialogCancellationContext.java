package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.response.ResponseUrlSender;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.webhook.WebhookResponse;
import lombok.*;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DialogCancellationContext extends Context implements SayUtility {

    private String responseUrl;
    private String channelId;
    private ResponseUrlSender responseUrlSender;

    public WebhookResponse respond(String text) throws IOException {
        return respond(ActionResponse.builder().text(text).build());
    }

    public WebhookResponse respond(List<LayoutBlock> blocks) throws IOException {
        return respond(ActionResponse.builder().blocks(blocks).build());
    }

    public WebhookResponse respond(ActionResponse response) throws IOException {
        if (responseUrlSender == null) {
            responseUrlSender = new ResponseUrlSender(slack, responseUrl);
        }
        return new ResponseUrlSender(slack, responseUrl).send(response);
    }

    public WebhookResponse respond(
            BuilderConfigurator<ActionResponse.ActionResponseBuilder> builder) throws IOException {
        return respond(builder.configure(ActionResponse.builder()).build());
    }

}
