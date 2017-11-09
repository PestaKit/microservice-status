/**
 * my simple api
 * An API to demonstrate Swagger
 *
 * OpenAPI spec version: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.avalia.fruits.api.dto;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Fruit
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-10-26T15:06:42.090+02:00")
public class Fruit   {
  @SerializedName("kind")
  private String kind = null;

  @SerializedName("colour")
  private String colour = null;

  @SerializedName("size")
  private String size = null;

  public Fruit kind(String kind) {
    this.kind = kind;
    return this;
  }

   /**
   * Get kind
   * @return kind
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public Fruit colour(String colour) {
    this.colour = colour;
    return this;
  }

   /**
   * Get colour
   * @return colour
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public Fruit size(String size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(example = "null", value = "")
  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Fruit fruit = (Fruit) o;
    return Objects.equals(this.kind, fruit.kind) &&
        Objects.equals(this.colour, fruit.colour) &&
        Objects.equals(this.size, fruit.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, colour, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Fruit {\n");
    
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    colour: ").append(toIndentedString(colour)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

