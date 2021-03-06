
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <stdio.h>
#include <axiom.h>
#include <axis2_util.h>
#include <axiom_soap.h>
#include <axis2_client.h>

axiom_node_t *build_om_payload_for_poclient_svc(
    const axutil_env_t * env);

int
main(
    int argc,
    char **argv)
{
    const axutil_env_t *env = NULL;
    const axis2_char_t *address = NULL;
    axis2_endpoint_ref_t *endpoint_ref = NULL;
    axis2_options_t *options = NULL;
    const axis2_char_t *client_home = NULL;
    axis2_svc_client_t *svc_client = NULL;
    axiom_node_t *payload = NULL;
    axiom_node_t *ret_node = NULL;
    int i = 0;

    /* Set up the environment */
    env = axutil_env_create_all("poclient.log", AXIS2_LOG_LEVEL_TRACE);

    /* Set up deploy folder. It is from the deploy folder, the configuration is picked up
     * using the axis2.xml file.
     * In this sample client_home points to the Axis2/C default deploy folder. The client_home can 
     * be different from this folder on your system. For example, you may have a different folder 
     * (say, my_client_folder) with its own axis2.xml file. my_client_folder/modules will have the 
     * modules that the client uses
     */
    client_home = AXIS2_GETENV("AXIS2C_HOME");
    if (!client_home || !strcmp(client_home, ""))
        client_home = "../..";

    for( i = 0; i < 5; i++)
    {
        if(i == 0)
        {
            address = "http://localhost:9090/svc_simulator_256b";
        }
        else if(i == 1)
        {
            address = "http://localhost:9090/svc_simulator_1k";
        }
        else if(i == 2)
        {
            address = "http://localhost:9090/svc_simulator_10k";
        }
        else if(i == 3)
        {
            address = "http://localhost:9090/svc_simulator_100k";
        }
        else
        {
            address = "http://localhost:9090/svc_simulator_1mb";
        }

        printf("Using endpoint : %s\n", address);

        /* Create EPR with given address */
        endpoint_ref = axis2_endpoint_ref_create(env, address);

        /* Setup options */
        options = axis2_options_create(env);
        axis2_options_set_to(options, env, endpoint_ref);
        axis2_options_set_action(options, env,
                                 "http://ws.apache.org/256b/c/samples/poclientString");

        /* Create service client */
        svc_client = axis2_svc_client_create(env, client_home);
        if (!svc_client)
        {
            printf
                ("Error creating service client, Please check AXIS2C_HOME again\n");
            AXIS2_LOG_ERROR(env->log, AXIS2_LOG_SI,
                            "Stub invoke FAILED: Error code:" " %d :: %s",
                            env->error->error_number,
                            AXIS2_ERROR_GET_MESSAGE(env->error));
            return -1;
        }

        /* Set service client options */
        axis2_svc_client_set_options(svc_client, env, options);

        /* Engage addressing module */
        axis2_svc_client_engage_module(svc_client, env, AXIS2_MODULE_ADDRESSING);

        /* Build the SOAP request message payload using OM API. */
        payload = build_om_payload_for_poclient_svc(env);

        /* Send request */
        ret_node = axis2_svc_client_send_receive(svc_client, env, payload);

        if (ret_node)
        {
            axis2_char_t *om_str = NULL;
            om_str = axiom_node_to_string(ret_node, env);
            if (om_str)
                printf("\nReceived OM : %s\n", om_str);
            printf("\npoclient client invoke SUCCESSFUL!\n");

            AXIS2_FREE(env->allocator, om_str);
            ret_node = NULL;
        }
        else
        {
            AXIS2_LOG_ERROR(env->log, AXIS2_LOG_SI,
                            "Stub invoke FAILED: Error code:" " %d :: %s",
                            env->error->error_number,
                            AXIS2_ERROR_GET_MESSAGE(env->error));
            printf("poclient client invoke FAILED!\n");
        }
        if (svc_client)
        {
            axis2_svc_client_free(svc_client, env);
            svc_client = NULL;
        }
    }
    if (env)
    {
        axutil_env_free((axutil_env_t *) env);
        env = NULL;
    }

    return 0;
}

/* build SOAP request message content using OM */
axiom_node_t *
build_om_payload_for_poclient_svc(
    const axutil_env_t * env)
{
    axiom_node_t *poclient_om_node = NULL;
    axiom_element_t *poclient_om_ele = NULL;
    axiom_node_t *text_om_node = NULL;
    axiom_element_t *text_om_ele = NULL;
    axiom_namespace_t *ns1 = NULL;
    axis2_char_t *om_str = NULL;

    ns1 = axiom_namespace_create(env, "http://ws.apache.org/axis2/services/poclient", "ns1");
    poclient_om_ele = axiom_element_create(env, NULL, "poclientString", ns1, &poclient_om_node);
    text_om_ele = axiom_element_create(env, poclient_om_node, "text", NULL, &text_om_node);
    axiom_element_set_text(text_om_ele, env, "Hello World!", text_om_node);
    om_str = axiom_node_to_string(poclient_om_node, env);
	axiom_namespace_free(ns1, env);

    if (om_str)
    {
        printf("\nSending OM : %s\n", om_str);
        AXIS2_FREE(env->allocator, om_str);
        om_str = NULL;
    }
    return poclient_om_node;
}
