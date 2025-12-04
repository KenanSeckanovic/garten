package com.acme.garten.controller;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.CLASS;

/// Annotation to put on Mapstruct mappers for generated classes to keep the annotation
@Retention(CLASS)
@interface ExcludeFromJacocoGeneratedReport {
}
