package com.yunsimon.oreoknife.process;

import com.google.auto.service.AutoService;
import com.yunsimon.oreoknife.annotations.OreoMethod;
import com.yunsimon.oreoknife.bind.OreoBinder;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by yunsimon on 2018/6/4.
 */

@AutoService(Processor.class)
public class OreoKnifeProcessor extends AbstractProcessor {
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
        mTypeUtils = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = new LinkedHashSet<>();
        annotationSet.add(OreoMethod.class.getCanonicalName());
        return annotationSet;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        processElement(roundEnvironment, OreoMethod.class, ElementKind.METHOD);
        return false;
    }

    private void processElement(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation, ElementKind kind) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
        for (Element element : elements) {
            if (element.getKind() != kind) {
                mMessager.printMessage(Diagnostic.Kind.ERROR, "use " + annotation.getClass() + " annotation err", element);
                return;
            }
            compileOreoCode(element, annotation);
        }
    }

    private void compileOreoCode(Element element, Class<? extends Annotation> annotation) {
        String pkgName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
        StringBuilder builder = new StringBuilder();
        String className = element.getEnclosingElement().getSimpleName().toString();
        String newClassName = className + OreoBinder.SUFFIX;
        builder.append("package " + pkgName + ";\n\n")
                .append("import android.util.Log;\n\n")
                .append("import com.yunsimon.oreoknife.bind.IBinder;\n\n")
                .append("public class ")
                .append(newClassName)
                .append(" <T extends " + className + ">")
                .append(" implements IBinder<T>")
                .append(" {\n\n")
                .append("\tpublic void bind(T target) {\n")
                .append("\t\tLog.e(\"xxx\", \"Oreo binder : " + ((OreoMethod) element.getAnnotation(annotation)).log() + "\");\n")
                .append("\t}\n")
                .append("}\n");
        try {
            JavaFileObject source = mFiler.createSourceFile(newClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, e.toString(), element);
        }
    }

}
