package org.javiermf.features.exceptions

import org.apache.commons.lang3.StringUtils

class WrongProductConfigurationException(evaluationErrorMessages: Set<String>) : Exception("Wrong product configuration: " + StringUtils.join(evaluationErrorMessages, ", "))
