package PDASimulator.DiagramGenerator;

import PDASimulator.DecidableTuringMachine.DecidableTuringMachine;
import PDASimulator.TransitionInfo.TransitionInfo;
import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.attribute.GraphAttr.SplineMode;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.util.HashMap;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.*;


public class DiagramGenerator {
    private DecidableTuringMachine turingMachine;

    public DiagramGenerator(DecidableTuringMachine tm) {
        this.turingMachine = tm;
    }

    public static String partialTransitionGraph(int width, String initialState, String finalState, TransitionInfo ti) {
        var g = graph("PDA").directed().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));
        var transitionNode = node(finalState).with(Shape.CIRCLE);
        var initialNode = node(initialState).with(Shape.CIRCLE);
        var invisibleStartNode = node("invisibleStartNode0").with(Shape.CIRCLE).with(Style.INVIS);
        var invisibleEndNode = node("invisibleEndNode-1").with(Shape.CIRCLE).with(Style.INVIS);
        g = g.with(
                invisibleStartNode.link(
                        to(
                                initialNode.link(
                                        to(
                                                transitionNode.link(
                                                        to(invisibleEndNode)
                                                )
                                        ).with(
                                                Label.of(ti.toString())
                                        )
                                )
                        )
                )
        );
        return Graphviz.fromGraph(g).width(width).render(Format.SVG).toString();
    }

    public static String generateSingleState(int width, String stateName, Boolean isFinal) {
        var g = graph("PDA").directed().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));
        var node = node(stateName).with(Shape.CIRCLE);
        if (isFinal) {
            node = node.with(Shape.DOUBLE_CIRCLE, Color.RED);
        }
        var invisibleStartNode = node("0").with(Shape.CIRCLE).with(Style.INVIS);
        var invisibleEndNode = node("-1").with(Shape.CIRCLE).with(Style.INVIS);
        g = g.with(invisibleStartNode.link(to(node.link(to(invisibleEndNode)))));
        return Graphviz.fromGraph(g).width(width).render(Format.SVG).toString();
    }

    public void setTuringMachine(DecidableTuringMachine tm) {
        this.turingMachine = tm;
    }

    private HashMap<String, Node> getNodes() {
        var map = new HashMap<String, Node>();
        for (var state : turingMachine.states) {
            var node = map.computeIfAbsent(state, Factory::node);
            if (turingMachine.finalStates.contains(state)) {
                node = node.with(Shape.DOUBLE_CIRCLE, Color.RED);
            } else {
                node = node.with(Shape.CIRCLE);
                if (state.equals(turingMachine.startState)) {
                    node = node.with(Color.GREEN);
                }
            }

            var transitions = turingMachine.transitions.get(state);
            if (transitions == null) {
                map.put(state, node);
                continue;
            }

            for (var transition : transitions.entrySet()) {
                for (var transitionInfo : transition.getValue()) {
                    var transitionNode = map.computeIfAbsent(transition.getKey(), Factory::node);
                    node = node.link(
                            to(transitionNode).with(
                                    Label.of(transitionInfo.toString())
                            )
                    );
                }
            }
            map.put(state, node);
        }
        return map;
    }

    @SafeVarargs
    private Graph getGraph(Attributes<? extends ForGraph>... attributes) {
        return graph("PDA").directed().graphAttr().with(
                Rank.dir(LEFT_TO_RIGHT),
                Label.of(turingMachine.languageDescription + "\n" + turingMachine.languageSetBuilderForm).locate(Label.Location.TOP),
                GraphAttr.splines(SplineMode.LINE)
        ).graphAttr().with(attributes);
    }


    public String generate() {
        var g = getGraph();
        var nodes = getNodes();
        for (var node : nodes.values()) {
            g = g.with(node);
        }
        return Graphviz.fromGraph(g).render(Format.SVG).toString();
    }

    @SafeVarargs
    public final String generateWithHighlightedState(String state, Color color, Attributes<? extends ForNode>... attributes) {
        if (!turingMachine.states.contains(state)) {
            throw new IllegalArgumentException("State not in states");
        }
        var g = getGraph();
        var nodes = getNodes();
        for (var node : nodes.values()) {
            g = g.with(node.name().toString().equals(state) ? node.with(color).with(attributes) : node);
        }
        return Graphviz.fromGraph(g).render(Format.SVG).toString();
    }

    @SafeVarargs
    public final String generateWithHighlightedTransition(String currentState, String finalState, TransitionInfo ti, Color color, Attributes<? extends ForLink>... attributes) {
        if (!turingMachine.states.contains(currentState)) {
            throw new IllegalArgumentException("State not in states");
        }
        var g = getGraph();
        var nodes = getNodes();
        for (var node : nodes.values()) {
            if (node.name().toString().equals(currentState)) {
                for (var link : node.links()) {
                    var label = (Label) link.attrs().get("label");

                    if (label != null && label.toString().equals(ti.toString())) {
                        var toNode = link.to();
                        if (toNode.name().toString().equals(currentState)) {
                            // set the gradient angle so the colors are split down the middle vertically
                            node = node.with(Color.BLUE.and(Color.BEIGE), Style.FILLED).with("gradientangle", "50");

                        } else {
                            node = node.with(Color.BLUE, Style.FILLED);
                        }
                        link.attrs().add(color);
                        for (var attr : attributes) {
                            link.attrs().add(attr);
                        }
                    }
                }
            } else if (node.name().toString().equals(finalState)) {
                node = node.with(Color.BEIGE, Style.FILLED);
            }
            g = g.with(node);
        }
        return Graphviz.fromGraph(g).render(Format.SVG).toString();
    }
}
