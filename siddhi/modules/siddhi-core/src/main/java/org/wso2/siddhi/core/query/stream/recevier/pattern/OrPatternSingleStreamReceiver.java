/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.siddhi.core.query.stream.recevier.pattern;

import org.apache.log4j.Logger;
import org.wso2.siddhi.core.event.StateEvent;
import org.wso2.siddhi.core.event.StreamEvent;
import org.wso2.siddhi.core.statemachine.pattern.OrPatternState;
import org.wso2.siddhi.core.query.stream.StreamProcessor;

public class OrPatternSingleStreamReceiver extends PatternSingleStreamReceiver {
    static final Logger log = Logger.getLogger(PatternSingleStreamReceiver.class);
    private int higherState;

    public OrPatternSingleStreamReceiver(OrPatternState state,
                                         StreamProcessor firstSimpleStreamProcessor,
                                         int complexEventSize) {
        super(state, firstSimpleStreamProcessor, complexEventSize);

        if (state.getStateNumber() < state.getPartnerState().getStateNumber()) {
            higherState = state.getPartnerState().getStateNumber();
        } else {
            higherState = state.getStateNumber();
        }

    }

    @Override
    public void receive(StreamEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("or state=" + currentState + " event=" + event + " ||currentEvents=" + currentEvents);
        }
//        System.out.println("next "+nextEvents);
        for (StateEvent currentEvent : currentEvents) {
            if (isEventsWithin(event, currentEvent)) {

                if (currentEvent.getEventState() != higherState) {
                    currentEvent.setStreamEvent(currentState, event);
                    firstSimpleStreamProcessor.process(currentEvent);
                }

                if (currentEvent.getEventState() < higherState) {
                    currentEvent.setStreamEvent(currentState, null);
                    try {
                        nextEvents.put(currentEvent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        currentEvents.clear();
//        }
    }

}
