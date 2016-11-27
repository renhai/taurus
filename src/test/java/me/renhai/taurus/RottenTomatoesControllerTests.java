/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.renhai.taurus;


import org.junit.Test;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class RottenTomatoesControllerTests extends AbstractTaurusTest {

    @Test
    public void noParamMovieShouldReturnError() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/rt/1.0/movies"))
        		.andDo(MockMvcResultHandlers.print())
        		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    
    @Test
    public void movieShouldReturnUnauthorizedError() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/rt/1.0/movies"))
        		.andDo(MockMvcResultHandlers.print())
        		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void paramMovieShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/rt/1.0/movies")
        	.with(SecurityMockMvcRequestPostProcessors.user(user).password(password).roles(roles))
        	.param("q", "sully"))
        	.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sully"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.year").value("2016"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cast").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.rating").isNotEmpty())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.link").isNotEmpty());
    }

}
