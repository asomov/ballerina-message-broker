/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package io.ballerina.messaging.broker.core.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;


public class ConsumerMetadata   {

  private @Valid Integer id = null;
  private @Valid String consumerTag = null;
  private @Valid Boolean isExclusive = null;
  private @Valid Boolean flowEnabled = null;
  private @Valid Integer channelId = null;
  private @Valid Integer connectionId = null;

  /**
   * unique id of the consumer
   **/
  public ConsumerMetadata id(Integer id) {
    this.id = id;
    return this;
  }


  @ApiModelProperty(required = true, value = "unique id of the consumer")
  @JsonProperty("id")
  @NotNull
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * identifier given by the channel
   **/
  public ConsumerMetadata consumerTag(String consumerTag) {
    this.consumerTag = consumerTag;
    return this;
  }


  @ApiModelProperty(required = true, value = "identifier given by the channel")
  @JsonProperty("consumerTag")
  @NotNull
  public String getConsumerTag() {
    return consumerTag;
  }
  public void setConsumerTag(String consumerTag) {
    this.consumerTag = consumerTag;
  }

  /**
   * State whether only this consumer can consume from the queue.
   **/
  public ConsumerMetadata isExclusive(Boolean isExclusive) {
    this.isExclusive = isExclusive;
    return this;
  }


  @ApiModelProperty(required = true, value = "State whether only this consumer can consume from the queue.")
  @JsonProperty("isExclusive")
  @NotNull
  public Boolean isIsExclusive() {
    return isExclusive;
  }
  public void setIsExclusive(Boolean isExclusive) {
    this.isExclusive = isExclusive;
  }

  /**
   * State whether the consumers is actively consuming messages
   **/
  public ConsumerMetadata flowEnabled(Boolean flowEnabled) {
    this.flowEnabled = flowEnabled;
    return this;
  }


  @ApiModelProperty(required = true, value = "State whether the consumers is actively consuming messages")
  @JsonProperty("flowEnabled")
  @NotNull
  public Boolean isFlowEnabled() {
    return flowEnabled;
  }
  public void setFlowEnabled(Boolean flowEnabled) {
    this.flowEnabled = flowEnabled;
  }


  public ConsumerMetadata channel(Integer channelId) {
    this.channelId = channelId;
    return this;
  }
  @ApiModelProperty(required = true, value = "Provide Channel ID")
  @JsonProperty("channelId")
  @NotNull
  public Integer getChannelId() {
    return channelId;
  }
  public void setChannelId(Integer channelId) {
    this.channelId = channelId;
  }

  public ConsumerMetadata connection(Integer connectionId) {
    this.connectionId = connectionId;
    return this;
  }
  @ApiModelProperty(required = true, value = "Provide Connection ID")
  @JsonProperty("connectionId")
  @NotNull
  public Integer getConnectionId() {
    return connectionId;
  }
  public void setConnectionId(Integer connectionId) {
    this.connectionId = connectionId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsumerMetadata consumerMetadata = (ConsumerMetadata) o;
    return Objects.equals(id, consumerMetadata.id) &&
           Objects.equals(consumerTag, consumerMetadata.consumerTag) &&
           Objects.equals(isExclusive, consumerMetadata.isExclusive) &&
           Objects.equals(flowEnabled, consumerMetadata.flowEnabled) &&
           Objects.equals(connectionId, consumerMetadata.connectionId) &&
           Objects.equals(channelId, consumerMetadata.channelId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, consumerTag, isExclusive, flowEnabled, channelId, connectionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsumerMetadata {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    consumerTag: ").append(toIndentedString(consumerTag)).append("\n");
    sb.append("    isExclusive: ").append(toIndentedString(isExclusive)).append("\n");
    sb.append("    flowEnabled: ").append(toIndentedString(flowEnabled)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channelId)).append("\n");
    sb.append("    connection: ").append(toIndentedString(connectionId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

